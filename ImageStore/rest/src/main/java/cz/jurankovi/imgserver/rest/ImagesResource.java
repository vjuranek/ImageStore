package cz.jurankovi.imgserver.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import cz.jurankovi.imgserver.model.rest.Image;

/**
 * REST endpoint for bulk image operations. 
 * 
 * @author vjuranek
 *
 */
@Path("/images")
public interface ImagesResource {
    
    @GET
    @Path("/")
    @Produces("application/json")
    public List<Image> getAll();
    
    @GET
    @Path("/uploaded")
    @Produces("application/json")
    public List<Image> getUploaded();

}
