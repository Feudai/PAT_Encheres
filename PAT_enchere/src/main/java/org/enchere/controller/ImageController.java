package org.enchere.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ImageController {
    private final String uploadDir = "C:/Users/pperrot12024/Documents/PATsave/";

    @GetMapping("/images/{cheminImage}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable("cheminImage") String cheminImage) {
        try {
            Path imagePath = Paths.get(uploadDir + cheminImage);
            Resource resource = new FileSystemResource(imagePath);
            
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/jpeg"))
                .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}