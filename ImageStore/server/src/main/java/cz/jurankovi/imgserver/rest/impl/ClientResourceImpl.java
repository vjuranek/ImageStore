package cz.jurankovi.imgserver.rest.impl;

import javax.inject.Inject;

import cz.jurankovi.imgserver.model.rest.ClientVersion;
import cz.jurankovi.imgserver.rest.ClientResource;
import cz.jurankovi.imgserver.service.ClientUpdateService;

public class ClientResourceImpl implements ClientResource {
    
    @Inject
    private ClientUpdateService updatesService;
    
    @Override
    public ClientVersion getCurrentVersion() {
        return updatesService.getClientVersion();
    }

}
