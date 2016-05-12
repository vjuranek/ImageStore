package cz.jurankovi.imgserver.rest.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import cz.jurankovi.imgserver.model.rest.Image;
import cz.jurankovi.imgserver.rest.ImageEndpoint;
import cz.jurankovi.imgserver.service.ImageService;

public class ImageEndpointImpl implements ImageEndpoint {

    private final int BUFFER_SIZE = 1024;
    
    @Inject
    private ImageService imgService;
    
    @Override
    public Response prepareUpload(@Context UriInfo uriInfo, Image image) {
        long id = imgService.prepareImageUpload(image);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder(); 
        uriBuilder.path(ImageEndpoint.class, "upload");
        System.out.println("LINK: " + uriBuilder.build(id));
        return Response.ok().link(uriBuilder.build(id), "upload").build();
    }
    
    @Override
    public Response upload(long imgId, InputStream imageStream) {
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
