package cz.jurankovi.imgserver.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import cz.jurankovi.imgserver.model.rest.ClientVersion;

/**
 * REST endpoint for the client. Provides namely update information for the client and updates themselves.
 * 
 * @author vjuranek
 *
 */

@Path("/client")
public interface Client {
    
    @GET
    @Path("/version")
    @Produces({ "application/xml" })
    public ClientVersion getCurrentVersion();
    
}
