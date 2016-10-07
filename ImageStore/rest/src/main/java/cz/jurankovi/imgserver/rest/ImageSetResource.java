package cz.jurankovi.imgserver.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/imageset")
public interface ImageSetResource {

    @POST
    @Path("/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    //@PartType(MediaType.APPLICATION_XML)
    public Response upload(MultipartFormDataInput input) throws Exception;
    
}
