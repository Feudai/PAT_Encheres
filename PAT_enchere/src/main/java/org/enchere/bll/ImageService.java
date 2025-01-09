package org.enchere.bll;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {

    public String sauvegarderImage(MultipartFile imageFile, int noArticle);
       
}
