package cz.jurankovi.imgserver.rest.impl;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

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
    
    @Inject
    private ImageService imgService;
    
    @Override
    public Response prepareUpload(@Context UriInfo uriInfo, Image image) {
        long id = imgService.prepareImageUpload(image);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder(); 
        uriBuilder.path(ImageEndpoint.class, "upload");
        return Response.ok().link(uriBuilder.build(id), "upload").build();
    }
    
    @Override
    public Response upload(long imgId, InputStream imageStream) {
        try {
            imgService.uploadImage(imgId, imageStream);
        } catch(IOException | NoSuchAlgorithmException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().build();
    }
    
}
