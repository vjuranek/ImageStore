package cz.jurankovi.imgserver.rest.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import cz.jurankovi.imgserver.rest.Image;

public class ImageImpl implements Image {

    private final int BUFFER_SIZE = 1024;
    
    @Override
    public Response uploadImage(long imgId, InputStream imageStream) {
        File f = new File(String.format("/tmp/%d.png", imgId));
        try (FileOutputStream fos = new FileOutputStream(f)) {
            byte[] buff = new byte[BUFFER_SIZE];
            int len = 0;
            while((len = imageStream.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch(IOException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().build();
    }
    
}
