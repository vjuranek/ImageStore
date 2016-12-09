package cz.jurankovi.imgserver.util;

import cz.jurankovi.imgserver.model.jpa.ImageEntity;
import cz.jurankovi.imgserver.model.rest.Image;

public class ModelMappers {

    public static Image ImageFromEntity(ImageEntity ent) {
        Image img = new Image();
        img.setName(ent.getName());
        img.setSha256(ent.getSha256());
        img.setPath(ent.getPath());
        img.setUploaded(ent.getUploaded());
        return img;
    }
    
}
