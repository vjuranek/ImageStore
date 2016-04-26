package cz.jurankovi.imgserver;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class ClientEndPointIT {

    private static final String CLIENT_VERSION_URL = "http://localhost:8080/imgserver/rest/client/version";

    @Test
    public void testClientVersion() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(getClientVersionStream(CLIENT_VERSION_URL));

        NodeList clientVerXml = doc.getElementsByTagName("clientVersion");
        assertEquals(1, clientVerXml.getLength());
        NamedNodeMap attrs = clientVerXml.item(0).getAttributes();
        assertEquals(0, Short.valueOf(attrs.getNamedItem("major").getTextContent()).shortValue());
        assertEquals(1, Short.valueOf(attrs.getNamedItem("minor").getTextContent()).shortValue());
    }

    public static InputStream getClientVersionStream(String clientVersionUrl) throws Exception {
        URL url = new URL(clientVersionUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        return conn.getInputStream();
    }
}
