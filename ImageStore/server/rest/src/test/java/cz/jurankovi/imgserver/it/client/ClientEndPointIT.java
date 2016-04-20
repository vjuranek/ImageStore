package cz.jurankovi.imgserver.it.client;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import cz.jurankovi.imgserver.client.Client;
import cz.jurankovi.imgserver.client.impl.ClientImpl;
import cz.jurankovi.imgserver.model.rest.ClientVersion;

@RunWith(Arquillian.class)
public class ClientEndPointIT {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(Client.class)
                .addClass(ClientImpl.class)
                .addClass(ClientVersion.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"));
    }

    @Inject
    private Client client;
    
    @Test
    public void testClientVersion() throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(ClientVersion.class);
        Marshaller marshaller = ctx.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(getClientVersion(), baos);
        
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new ByteArrayInputStream(baos.toByteArray()));
        NodeList clientVerXml = doc.getElementsByTagName(ClientImpl.VERSION_TAG);
        assertEquals(1, clientVerXml.getLength());
        NamedNodeMap attrs = clientVerXml.item(0).getAttributes();
        assertEquals(ClientImpl.VERSION_MAJOR, Short.valueOf(attrs.getNamedItem("major").getTextContent()).shortValue());
        assertEquals(ClientImpl.VERSION_MINOR, Short.valueOf(attrs.getNamedItem("minor").getTextContent()).shortValue());
    }
    
    private ClientVersion getClientVersion() {
        return client.getCurrentVersion();
    }
}
