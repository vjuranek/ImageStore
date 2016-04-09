package cz.jurankovi.imgserver.client.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import cz.jurankovi.imgserver.client.Client;

public class ClientImplTest {

    private static Client client;
    private static DocumentBuilder docBuilder;

    @BeforeClass
    public static void init() throws ParserConfigurationException {
        client = new ClientImpl();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        docBuilder = dbFactory.newDocumentBuilder();
    }

    @Test
    public void testCurrnetVersion() throws Exception {
        String versionXml = client.getCurrentVersion();
        Document doc = docBuilder.parse(new ByteArrayInputStream(versionXml.getBytes()));
        assertEquals(String.format("%d.%d", ClientImpl.VERSION_MAJOR, ClientImpl.VERSION_MINOR),
                doc.getElementsByTagName(ClientImpl.VERSION_TAG).item(0).getTextContent());
    }
}
