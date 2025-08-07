package com.crownydev.CrownyHotel.service.impl;

import com.crownydev.CrownyHotel.exception.OurException;
import com.crownydev.CrownyHotel.service.interfac.IFileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service // Mark this as our active file storage service
public class LocalFileStorageServiceImpl implements IFileStorageService {

    private final Path rootLocation;

    // We will define this property in application.properties
    public LocalFileStorageServiceImpl(@Value("${file.storage.local.directory}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new OurException("Could not initialize storage location: " + e.getMessage());
        }
    }

    @Override
    public String saveFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new OurException("Failed to store empty file.");
            }

            // Generate a unique filename to prevent conflicts
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;


            Path destinationFile = this.rootLocation.resolve(Paths.get(uniqueFileName))
                    .normalize().toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            // Return the unique filename that we can save in the database
            return uniqueFileName;

        } catch (IOException e) {
            throw new OurException("Failed to store file: " + e.getMessage());
        }
    }

    @Override
    public byte[] downloadFile(String fileIdentifier) {
        try {
            Path file = rootLocation.resolve(fileIdentifier);
            return Files.readAllBytes(file);
        } catch (IOException e) {
            throw new OurException("Failed to read file: " + fileIdentifier + ". Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String fileIdentifier) {
        try {
            Path file = rootLocation.resolve(fileIdentifier);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new OurException("Failed to delete file: " + fileIdentifier + ". Error: " + e.getMessage());
        }
    }
}