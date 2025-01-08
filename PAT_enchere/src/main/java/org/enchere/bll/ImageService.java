package org.enchere.bll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private final String uploadDir = "C:/Users/pperrot12024/Documents/PATsave/";

    public String sauvegarderImage(MultipartFile file, int noArticle) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide");
        }

        // Créer le dossier si nécessaire
        Path cheminDossier = Paths.get(uploadDir);
        if (!Files.exists(cheminDossier)) {
            Files.createDirectories(cheminDossier);
        }

        // Renommer l'image avec le numéro d'article
        String extension = Objects.requireNonNull(file.getOriginalFilename())
                                  .substring(file.getOriginalFilename().lastIndexOf("."));
        String nomFichier = "article_" + noArticle + extension;

        // Sauvegarder l'image
        Path cheminFichier = cheminDossier.resolve(nomFichier);
        Files.copy(file.getInputStream(), cheminFichier, StandardCopyOption.REPLACE_EXISTING);

        // Retourner le chemin relatif pour l'affichage
        return "/images/" + nomFichier;  // Chemin relatif
    }

}
