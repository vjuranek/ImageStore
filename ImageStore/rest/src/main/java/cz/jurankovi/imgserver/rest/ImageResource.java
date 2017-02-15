package cz.jurankovi.imgserver.rest;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import cz.jurankovi.imgserver.jaxb.ValidateXML;
import cz.jurankovi.imgserver.model.rest.Image;

/**
 * REST endpoint for manipulating with images.
 * 
 * @author vjuranek
 *
 */
@Path("/image")
public interface ImageResource {
    
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_XML)
    @ValidateXML
    public Response prepareUpload(@Context UriInfo uriInfo, Image image);
    
    @POST
    @Path("/{imgId}")
    @Consumes("image/*;charset=UTF-8")
    public Response upload(@PathParam("imgId") Long imgId, InputStream imageStream);
}
