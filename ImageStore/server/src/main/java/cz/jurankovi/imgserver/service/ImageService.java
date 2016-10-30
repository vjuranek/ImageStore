package cz.jurankovi.imgserver.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import cz.jurankovi.imgserver.model.jpa.ImageEntity;
import cz.jurankovi.imgserver.model.rest.Image;
import cz.jurankovi.imgserver.util.Configuration;

@Stateless
public class ImageService {

    private static final int BUFFER_SIZE = 1024;
    private static final String HASH_ALG = "SHA-256";
    
    @Inject
    @Configuration(property = "upload.directory")
    private String uploadDir;

    @Inject
    private EntityManager em;

    public long prepareImageUpload(Image image) {
        ImageEntity ie = new ImageEntity();
        ie.setClientId(1);
        ie.setName(image.getName());
        ie.setSha256(image.getSha256());
        ie.setCreated(new Date());
        ie.setUploadFinished(false);

        em.persist(ie);
        return ie.getId();
    }

    public void uploadImage(long imgId, InputStream imageStream) throws IOException, IllegalStateException, NoSuchAlgorithmException {
        String uploadedImgPath = String.format(uploadDir + "/%d.png", imgId);
        String uploadedSHA = digestToString(copyStream(uploadedImgPath, imageStream));
        
        ImageEntity ie = em.find(ImageEntity.class, imgId);
        if (!uploadedSHA.equals(ie.getSha256())) {
            throw new IllegalStateException(String.format("SHA256 doesn match! Expected: %s, uploaded: %s", ie.getSha256(), uploadedSHA));
        }
        
        ie.setPath(uploadedImgPath);
        ie.setUploaded(new Date());
        ie.setUploadFinished(true);
    }
    
    /*package*/ byte[] copyStream(String uploadPath, InputStream imageStream) throws IOException, NoSuchAlgorithmException {
        File f = new File(uploadPath);
        MessageDigest md = MessageDigest.getInstance(HASH_ALG);
        try (FileOutputStream fos = new FileOutputStream(f)) {
            byte[] buff = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = imageStream.read(buff)) != -1) {
                fos.write(buff, 0, len);
                md.update(buff, 0, len);
            }
        }
        return md.digest();
    }
    
    /*package*/ String digestToString(byte[] digest) {
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
