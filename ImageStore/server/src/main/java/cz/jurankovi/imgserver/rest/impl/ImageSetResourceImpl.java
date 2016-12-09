package cz.jurankovi.imgserver.rest.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import cz.jurankovi.imgserver.model.rest.Image;
import cz.jurankovi.imgserver.rest.ImageSetResource;
import cz.jurankovi.imgserver.service.ImageService;
import cz.jurankovi.imgserver.util.Functions;

public class ImageSetResourceImpl implements ImageSetResource {

    @Inject
    private ImageService imgService;

    @Override
    public Response upload(MultipartFormDataInput input) throws Exception {
        Map<String, List<InputPart>> parts = input.getFormDataMap();
        List<InputPart> imgParts = parts.get("images");
        List<InputPart> files = parts.get("files");
        if (imgParts.size() != files.size()) {
            // TODO log reason fo the error
            System.out.println("!!!ruzne velikosti img a files");
            return Response.serverError().build();
        }
        
        List<Image> images = imgParts.stream()
                .map(imgPart -> { try { 
                    return imgPart.getBody(Image.class, null);
                } catch(IOException e) {
                  throw new UncheckedIOException(e);  
                }})
                .collect(Collectors.toList());
        Map<String, InputPart> fileNames = files.stream()
                .collect(Collectors.toMap(file -> Functions.fileNameFromFormHeaders(file.getHeaders()), file -> file));

        fileNames.keySet().forEach(file -> System.out.println("Filename: " + file));
        
        for (Image img : images) {
            if (!fileNames.containsKey(img.getName())) {
                // TODO log reason fo the error
                System.out.println("!!!spatne mapovani img <-> file, img: " + img.getName());
                return Response.serverError().build();
            }

            // TODO add method to do whole upload in one transaction
            long imgId = imgService.prepareImageUpload(img);
            imgService.uploadImage(imgId, fileNames.get(img.getName()).getBody(InputStream.class, null));
        }

        return Response.ok().build();
    }

}
