package cz.jurankovi.imgserver.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import cz.jurankovi.imgserver.model.jpa.ImageEntity;

/**
 * Service for bulk image operations
 * 
 * @author vjuranek
 *
 */
@Stateless
public class ImagesService {
    
    @Inject
    private EntityManager em;

    public List<ImageEntity> getAllImages() {
        return em.createNamedQuery("getAllImages", ImageEntity.class).getResultList();
    }
    
    public List<ImageEntity> getUploadedImages() {
        return em.createNamedQuery("getAllUploadedImages", ImageEntity.class).getResultList();
    }

}
