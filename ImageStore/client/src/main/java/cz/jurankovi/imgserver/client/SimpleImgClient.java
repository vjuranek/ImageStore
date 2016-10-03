package cz.jurankovi.imgserver.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.ws.rs.core.Response;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.resteasy.client.jaxrs.ClientHttpEngine;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;

import cz.jurankovi.imgserver.model.rest.Image;
import cz.jurankovi.imgserver.rest.ImageResource;

public class SimpleImgClient {

    private static final int BUFFER_SIZE = 1024;
    private static final String HASH_ALG = "SHA-256";

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Exactly one parameter (path to image) required, got " + args.length);
            System.exit(1);
        }
        String imgPath = args[0];
        File file = new File(imgPath);
        String imgSha256 = digestToString(sha256(file));
        Image img = new Image();
        img.setName(imgPath);
        img.setSha256(imgSha256);

        System.setProperty("javax.net.ssl.trustStore", "src/main/resources/truststore_client.jks");
        //System.setProperty("javax.net.ssl.trustStore", "src/main/resources/rhcloud_truststore.jks");
        System.setProperty("javax.net.ssl.keyStore", "src/main/resources/keystore_client.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","secret");
        HttpClientBuilder builder = HttpClientBuilder.create();
        CredentialsProvider credentials = new BasicCredentialsProvider();
        credentials.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("testclient", "testpassword"));
        HttpClient httpClient = builder.setDefaultCredentialsProvider(credentials).build();
        builder.setSSLSocketFactory(getSSLConnectionSocketFactory());

        ClientHttpEngine engine = new ApacheHttpClient4Engine(httpClient);
        ResteasyClient client = new ResteasyClientBuilder().httpEngine(engine).build();
        ResteasyWebTarget target = (ResteasyWebTarget) client.target("https://localhost:8443/imgserver/rest");
        //ResteasyWebTarget target = (ResteasyWebTarget) client.target("https://imgserver-vjuranek.rhcloud.com/imgserver/rest");
        ImageResource imgRes = target.proxy(ImageResource.class);

        Response res = imgRes.prepareUpload(null, img);
        if (Response.Status.OK != res.getStatusInfo()) {
            // TODO hande failure
        }
        URI uploadURI = res.getLink("upload").getUri();
        System.out.println("upload to " + uploadURI);
        // TODO really use URI, it can be different server than one with DB
        Long imgId = Long.valueOf(res.getHeaderString("imgId"));
        res.close();

        try (InputStream is = new FileInputStream(file)) {
            res = imgRes.upload(imgId, is);
            if (Response.Status.OK != res.getStatusInfo()) {
                // TODO hande failure
            }
        } finally {
            res.close();
        }
    }

    private static SSLConnectionSocketFactory getSSLConnectionSocketFactory() throws Exception {
        KeyStore clientTrustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        KeyStore clientKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        clientKeyStore.load(SimpleImgClient.class.getResourceAsStream("keystore_client.jks"), "secret".toCharArray());
        //clientKeyStore.load(new FileInputStream(new File("/home/vjuranek/ws_img/ImageStore/ImageStore/client/src/main/resources/keystore_client.jks")), "secret".toCharArray());
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(clientTrustStore)
                .loadKeyMaterial(clientKeyStore, "secret".toCharArray())
                //.useTLS()
                .build();
        //return new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.STRICT_HOSTNAME_VERIFIER);
        return new SSLConnectionSocketFactory(sslContext);
    }

    private static byte[] sha256(File f) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(HASH_ALG);
        try (FileInputStream fis = new FileInputStream(f)) {
            byte[] buff = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = fis.read(buff)) != -1) {
                md.update(buff, 0, len);
            }
        }
        return md.digest();
    }

    private static String digestToString(byte[] digest) {
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
