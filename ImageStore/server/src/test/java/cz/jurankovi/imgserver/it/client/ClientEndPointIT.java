package cz.jurankovi.imgserver.it.client;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;

import cz.jurankovi.imgserver.client.Client;
import cz.jurankovi.imgserver.client.impl.ClientImpl;

@RunWith(Arquillian.class)
public class ClientEndPointIT {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(Client.class)
                .addClass(ClientImpl.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"));
    }

    @Inject
    Client client;
    
    @Test
    public void should_create_greeting() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new ByteArrayInputStream(getClientVersion().getBytes()));
        String realVersion = doc.getElementsByTagName(ClientImpl.VERSION_TAG).item(0).getTextContent();
        assertEquals(String.format("%d.%d", ClientImpl.VERSION_MAJOR, ClientImpl.VERSION_MINOR), realVersion);
    }
    
    private String getClientVersion() {
        return client.getCurrentVersion();
    }
}
