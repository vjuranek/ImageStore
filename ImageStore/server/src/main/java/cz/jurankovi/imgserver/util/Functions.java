package cz.jurankovi.imgserver.util;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.MultivaluedMap;

import cz.jurankovi.imgserver.model.jpa.ImageEntity;
import cz.jurankovi.imgserver.model.rest.Image;

public class Functions {

    public static List<Image> imgEntListToImg(List<ImageEntity> imgEntLits) {
        return imgEntLits.stream().map(ent -> ModelMappers.ImageFromEntity(ent)).collect(Collectors.toList());
    }
    
    public static String imgListToString(List<ImageEntity> imgList) {
        StringBuilder sb = new StringBuilder();
        imgList.forEach(it -> {
            sb.append(it.toString());
            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }

    public static String imgListToJson(List<ImageEntity> imgList) {
        StringBuilder sb = new StringBuilder("[");
        imgList.forEach(it -> {
            sb.append(it.toJson());
            sb.append(",");
        });
        sb.setCharAt(sb.length()-1, ']');
        return sb.toString();
    }
    
    /**
     * Extract file name from HTTP multipart/form-data request headers for file upload request.
     * 
     * @param headers HTTP header of on part of  multipart/form-data HTTP request.
     * @return File name of file which is uploaded in given part of multipart/form-data HTTP request.
     */
    public static String fileNameFromFormHeaders(MultivaluedMap<String, String> headers) {
        for (String part : headers.getFirst("Content-Disposition").split(";")) {
            if (part.trim().startsWith("filename")) {
                return part.split("=")[1].trim().replaceAll("\"", "");
            }
        }
        throw new IllegalStateException("Content-Disposition header doesn't contain 'filename' part");
    }

}
