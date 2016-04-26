package cz.jurankovi.imgserver.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import cz.jurankovi.imgserver.model.jpa.ClientVersionEntity;
import cz.jurankovi.imgserver.model.rest.ClientVersion;

@Stateless
public class ClientUpdates {
    
    @Inject
    private EntityManager em;
    
    public ClientVersion getClientVersion() {
        ClientVersionEntity cve = em.find(ClientVersionEntity.class, 1);
        ClientVersion clientVersion = new ClientVersion();
        clientVersion.setMajor(cve.getMajor());
        clientVersion.setMinor(cve.getMinor());
        return clientVersion;
    }
}
