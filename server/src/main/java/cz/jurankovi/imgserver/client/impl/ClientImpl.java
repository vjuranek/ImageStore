package cz.jurankovi.imgserver.client.impl;

import cz.jurankovi.imgserver.client.Client;

public class ClientImpl implements Client {
    
    //TODO: load from external source
    public static final short VERSION_MAJOR = 0;
    public static final short VERSION_MINOR = 1;
    public static final String VERSION_TAG = "version";
    
    
    @Override
    public String getCurrentVersion() {
        return String.format("<?xml version=\"1.0\"?><%s>%d.%d</%s>", VERSION_TAG, VERSION_MAJOR, VERSION_MINOR, VERSION_TAG);
    }

}
