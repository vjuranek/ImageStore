package cz.jurankovi.imgserver.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Test endpoing for clients to test connection and REST requests
 * 
 * @author vjuranek
 *
 */
@Path("/test")
public interface TestResource {
    
    @GET
    @Path("/")
    public Response testReply();

}
