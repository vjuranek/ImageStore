package cz.jurankovi.imgserver.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cz.jurankovi.imgserver.model.rest.ClientVersion;

/**
 * REST endpoint for the client. Provides namely update information for the client and updates themselves.
 * 
 * @author vjuranek
 *
 */

@Path("/client")
public interface ClientEndpoint {
    
    @GET
    @Path("/version")
    @Produces({ MediaType.APPLICATION_XML })
    public ClientVersion getCurrentVersion();
    
}
