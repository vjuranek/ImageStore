package cz.jurankovi.imgserver.rest.impl;

import java.util.List;

import javax.inject.Inject;

import cz.jurankovi.imgserver.model.jpa.ImageEntity;
import cz.jurankovi.imgserver.model.rest.Image;
import cz.jurankovi.imgserver.rest.ImagesResource;
import cz.jurankovi.imgserver.service.ImagesService;
import cz.jurankovi.imgserver.util.Functions;

public class ImagesResourceImpl implements ImagesResource {

    @Inject
    private ImagesService imgsService;

    @Override
    public List<Image> getAll() {
        List<ImageEntity> imgs = imgsService.getAllImages();
        //return Functions.imgListToString(imgs);
        //return Functions.imgListToJson(imgs);
        return Functions.imgEntListToImg(imgs);
    }

    @Override
    public List<Image> getUploaded() {
        List<ImageEntity> imgs = imgsService.getUploadedImages();
        //return Functions.imgListToString(imgs);
        //return Functions.imgListToJson(imgs);
        return Functions.imgEntListToImg(imgs);
    }

}
