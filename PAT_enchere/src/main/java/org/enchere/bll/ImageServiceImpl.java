package org.enchere.bll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService {
    private final String uploadDir = System.getProperty("user.home") + "\\Documents/";

    public String sauvegarderImage(MultipartFile imageFile, int noArticle) {
        try {
            String fileName = noArticle + "_" + UUID.randomUUID().toString() + ".jpg";
            Path path = Paths.get(uploadDir + fileName);
            Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            // On retourne seulement le nom du fichier
            return fileName; 
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de l'image", e);
        }
    }
}