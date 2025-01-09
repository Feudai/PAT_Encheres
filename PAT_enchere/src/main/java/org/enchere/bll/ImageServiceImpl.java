package org.enchere.bll;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.enchere.exceptions.ImageTropGrandException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService {
    private final String uploadDir = System.getProperty("user.home") + "\\Documents/";
    private final long MAX_FILE_SIZE = 1 * 1024 * 1024; // 1MB en bytes

    public String sauvegarderImage(MultipartFile imageFile, int noArticle) {
        try {
            // Vérification de la taille
            if (imageFile.getSize() > MAX_FILE_SIZE) {
                throw new ImageTropGrandException("La taille du fichier dépasse la limite de 5MB");
            }

            // Vérification du type MIME
            String contentType = imageFile.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("Le fichier doit être une image");
            }

            String fileName = noArticle + "_" + UUID.randomUUID().toString() + ".jpg";
            Path path = Paths.get(uploadDir + fileName);
            Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de l'image", e);
        }
    }
}