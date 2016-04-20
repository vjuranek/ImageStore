package cz.jurankovi.imgserver.client.impl;

import static org.junit.Assert.assertEquals;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.junit.Test;

import cz.jurankovi.imgserver.client.Client;
import cz.jurankovi.imgserver.model.ClientVersion;

public class ClientImplTest {

    private static Client client;

    @BeforeClass
    public static void init() throws ParserConfigurationException {
        client = new ClientImpl();
    }

    @Test
    public void testCurrnetVersion() throws Exception {
        ClientVersion versionXml = client.getCurrentVersion();
        assertEquals(ClientImpl.VERSION_MAJOR, versionXml.getMajor());
        assertEquals(ClientImpl.VERSION_MINOR, versionXml.getMinor());
    }
}
