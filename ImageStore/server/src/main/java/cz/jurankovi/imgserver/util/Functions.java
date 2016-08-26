package cz.jurankovi.imgserver.util;

import java.util.List;

import cz.jurankovi.imgserver.model.jpa.ImageEntity;

public class Functions {

    public static String imgListToString(List<ImageEntity> imgList) {
        StringBuilder sb = new StringBuilder();
        imgList.forEach(it -> { 
            sb.append(it.toString());
            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }

}
