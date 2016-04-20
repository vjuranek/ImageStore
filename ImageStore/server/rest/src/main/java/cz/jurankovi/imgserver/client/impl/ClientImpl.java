package cz.jurankovi.imgserver.client.impl;

import cz.jurankovi.imgserver.client.Client;
import cz.jurankovi.imgserver.model.rest.ClientVersion;

public class ClientImpl implements Client {
    
    //TODO: load from external source
    public static final short VERSION_MAJOR = 0;
    public static final short VERSION_MINOR = 1;
    public static final String VERSION_TAG = "clientVersion";
    
    
    @Override
    public ClientVersion getCurrentVersion() {
        ClientVersion clientVersion = new ClientVersion();
        clientVersion.setMajor(VERSION_MAJOR);
        clientVersion.setMinor(VERSION_MINOR);
        return clientVersion;
    }

}
