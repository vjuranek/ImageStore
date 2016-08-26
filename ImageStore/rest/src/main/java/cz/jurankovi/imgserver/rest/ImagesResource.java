package cz.jurankovi.imgserver.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
    @Produces("text/plain")
    public String getAll();
    
    @GET
    @Path("/uploaded")
    @Produces("text/plain")
    public String getUploaded();

}
