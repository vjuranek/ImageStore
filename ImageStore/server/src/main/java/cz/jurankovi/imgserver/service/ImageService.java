package cz.jurankovi.imgserver.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import cz.jurankovi.imgserver.model.jpa.ImageEntity;
import cz.jurankovi.imgserver.model.rest.Image;

@Stateless
public class ImageService {
    
    @Inject
    private EntityManager em;
    
    public long prepareImageUpload(Image image) {
        ImageEntity ie = new ImageEntity();
        ie.setClientId(1);
        ie.setName(image.getName());
        ie.setPath("/tmp/" + image.getName());
        ie.setSha256("12345");
        ie.setUploadFinished(false);
        
        em.persist(ie);
        return ie.getId();
    }

}
