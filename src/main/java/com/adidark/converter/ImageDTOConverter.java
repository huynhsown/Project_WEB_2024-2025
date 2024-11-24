package com.adidark.converter;

import com.adidark.entity.ImageEntity;
import org.springframework.stereotype.Component;

@Component
public class ImageDTOConverter {

    public ImageEntity urlToImageEntity(String url){
        ImageEntity result = new ImageEntity();
        result.setURL(url);
        return result;
    }

}
