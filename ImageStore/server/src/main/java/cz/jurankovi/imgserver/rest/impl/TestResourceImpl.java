package cz.jurankovi.imgserver.rest.impl;

import javax.ws.rs.core.Response;

import cz.jurankovi.imgserver.rest.TestResource;

public class TestResourceImpl implements TestResource {

    @Override
    public Response testReply() {
        return Response.ok("<test>test element</test>").build();
    }

}
