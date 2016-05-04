package cz.jurankovi.imgserver.rest;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * REST endpoint for manipulating with images.
 * 
 * @author vjuranek
 *
 */
@Path("/image")
public interface Image {

    @PUT
    @Path("/{imgId}")
    @Consumes("image/*;charset=UTF-8")
    public Response uploadImage(@PathParam("imgId") long imgId, InputStream imageStream);
}
