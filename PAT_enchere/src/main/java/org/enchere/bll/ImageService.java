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
public class ImageService {

    private final String uploadDir = "C:/Users/pperrot12024/Documents/PATsave/";

    public String sauvegarderImage(MultipartFile imageFile, int noArticle) {
        try {
            // Générer un nom de fichier unique
            String fileName = noArticle + "_" + UUID.randomUUID().toString() + ".jpg";
            
            // Sauvegarder le fichier
            Path path = Paths.get(uploadDir + fileName);
            Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            
            // Retourner uniquement le nom du fichier, pas le chemin complet
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de l'image", e);
        }
    }}
