package cz.jurankovi.imgserver.rest.impl;

import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import cz.jurankovi.imgserver.model.rest.Client;
import cz.jurankovi.imgserver.model.rest.ClientVersion;
import cz.jurankovi.imgserver.rest.ClientResource;
import cz.jurankovi.imgserver.service.ClientService;

public class ClientResourceImpl implements ClientResource {
    
    @Inject
    private ClientService clientService;
    
    @Override
    public Client getClient(@PathParam("clientId") Long clientId) {
        return clientService.getClient(clientId);
    }
    
    @Override
    public Response createClient(UriInfo uriInfo, ClientVersion clientVersion) {
        long clientId = clientService.createClient(clientVersion);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder(); 
        uriBuilder.path(ClientResource.class, "getClient");
        return Response.ok().link(uriBuilder.build(clientId), "client").build();
    }
    
    @Override
    public ClientVersion getCurrentVersion() {
        return clientService.getClientVersion();
    }

}
