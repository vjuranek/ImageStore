package cz.jurankovi.imgserver.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import cz.jurankovi.imgserver.model.jpa.ClientEntity;
import cz.jurankovi.imgserver.model.jpa.ClientVersionEntity;
import cz.jurankovi.imgserver.model.rest.Client;
import cz.jurankovi.imgserver.model.rest.ClientVersion;

@Stateless
public class ClientService {
    
    @Inject
    private EntityManager em;
    
    public long createClient(ClientVersion clientVersion) {
        Query q = em.createNamedQuery(ClientVersionEntity.GET_CLIENT_VERTION_QUERY)
                .setParameter("major", clientVersion.getMajor())
                .setParameter("minor", clientVersion.getMinor());
        ClientVersionEntity cve = (ClientVersionEntity)q.getResultList().get(0);
        
        ClientEntity client = new ClientEntity(cve);
        em.persist(client);
        return client.getId();
    }
    
    public Client getClient(Long clientId) {
        ClientEntity ce = em.find(ClientEntity.class, clientId);
        ClientVersionEntity cve = ce.getClientVersion();
        ClientVersion clientVersion = new ClientVersion(); 
        //TODO use apater
        clientVersion.setMajor(cve.getMajor());
        clientVersion.setMinor(cve.getMinor());
        clientVersion.setReleased(cve.getReleased());
        Client client = new Client();
        client.setClientVersion(clientVersion);
        return client;
    }
    
    public ClientVersion getClientVersion() {
        ClientVersionEntity cve = em.find(ClientVersionEntity.class, 1);
        ClientVersion clientVersion = new ClientVersion();
        clientVersion.setMajor(cve.getMajor());
        clientVersion.setMinor(cve.getMinor());
        clientVersion.setReleased(cve.getReleased());
        return clientVersion;
    }
}
