package cz.jurankovi.imgserver.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class ImageServiceTest {
    
    @Test
    public void testDigestToString() {
        byte[] digest = {78,-64,16,-118,-80,39,-81,25,-12,55,-29,104,101,-99,67,60,124,64,-43,-113,27,115,107,-10,95,109,-88,-18,-121,-38,117,14};
        ImageService imgsrv = new ImageService();
        assertEquals("4ec0108ab027af19f437e368659d433c7c40d58f1b736bf65f6da8ee87da750e", imgsrv.digestToString(digest));
    }

}
