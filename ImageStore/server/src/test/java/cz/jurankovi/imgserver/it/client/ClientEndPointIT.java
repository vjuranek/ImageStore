package cz.jurankovi.imgserver.it.client;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import cz.jurankovi.imgserver.model.jpa.ClientVersionEntity;
import cz.jurankovi.imgserver.model.rest.ClientVersion;
import cz.jurankovi.imgserver.rest.client.Client;
import cz.jurankovi.imgserver.rest.client.impl.ClientImpl;
import cz.jurankovi.imgserver.service.ClientUpdates;
import cz.jurankovi.imgserver.util.Resources;

@RunWith(Arquillian.class)
public class ClientEndPointIT {
    
    public static final String VERSION_TAG = "clientVersion";
    //TODO: load from external source
    public static final short VERSION_MAJOR = 0;
    public static final short VERSION_MINOR = 1;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(Client.class)
                .addClass(ClientImpl.class)
                .addClass(ClientVersion.class)
                .addClass(ClientVersionEntity.class)
                .addClass(ClientUpdates.class)
                .addClass(Resources.class)
                .addAsWebInfResource(new File("src/test/resources/META-INF/persistence.xml"), "classes/META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"));
    }

    @Inject
    private Client client;
    
    @Inject
    EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    @Before
    public void prepareDB() throws Exception {
        ClientVersionEntity cve = new ClientVersionEntity();
        cve.setMajor((short)0);
        cve.setMinor((short)1);
        
        utx.begin();
        em.joinTransaction();
        em.persist(cve);
        utx.commit();
    }
    
    @Test
    public void testClientVersion() throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(ClientVersion.class);
        Marshaller marshaller = ctx.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(getClientVersion(), baos);
        
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new ByteArrayInputStream(baos.toByteArray()));
        NodeList clientVerXml = doc.getElementsByTagName(VERSION_TAG);
        assertEquals(1, clientVerXml.getLength());
        NamedNodeMap attrs = clientVerXml.item(0).getAttributes();
        assertEquals(VERSION_MAJOR, Short.valueOf(attrs.getNamedItem("major").getTextContent()).shortValue());
        assertEquals(VERSION_MINOR, Short.valueOf(attrs.getNamedItem("minor").getTextContent()).shortValue());
    }
    
    private ClientVersion getClientVersion() {
        return client.getCurrentVersion();
    }
}
