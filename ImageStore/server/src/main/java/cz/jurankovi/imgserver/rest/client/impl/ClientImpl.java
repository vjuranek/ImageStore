package cz.jurankovi.imgserver.rest.client.impl;

import javax.inject.Inject;

import cz.jurankovi.imgserver.rest.client.Client;
import cz.jurankovi.imgserver.model.rest.ClientVersion;
import cz.jurankovi.imgserver.service.ClientUpdates;

public class ClientImpl implements Client {
    
    @Inject
    private ClientUpdates updatesService;
    
    @Override
    public ClientVersion getCurrentVersion() {
        return updatesService.getClientVersion();
    }

}
