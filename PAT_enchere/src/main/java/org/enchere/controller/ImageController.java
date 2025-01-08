package org.enchere.controller;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ImageController {

    private final String uploadDir = "C:/Users/pperrot12024/Documents/PATsave/";

    @GetMapping("/images/{cheminImage}")
    public Resource getImage(@PathVariable String cheminImage) {
        File imageFile = new File(uploadDir + cheminImage);
        if (!imageFile.exists()) {
            throw new RuntimeException("Image non trouvée : " + cheminImage);
        }
        return new FileSystemResource(imageFile);
    }

}