package cz.jurankovi.imgserver.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import cz.jurankovi.imgserver.model.rest.Client;
import cz.jurankovi.imgserver.model.rest.ClientVersion;

/**
 * REST endpoint for the client. Provides namely update information for the client and updates themselves.
 * 
 * @author vjuranek
 *
 */

@Path("/client")
public interface ClientResource {
    
    @GET
    @Path("/{clientId}")
    @Produces(MediaType.APPLICATION_XML)
    public Client getClient(@PathParam("clientId") Long clientId);
    
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_XML)
    public Response createClient(@Context UriInfo uriInfo, ClientVersion clientVersion);
    
    @GET
    @Path("/version")
    @Produces({ MediaType.APPLICATION_XML })
    public ClientVersion getCurrentVersion();
    
}
