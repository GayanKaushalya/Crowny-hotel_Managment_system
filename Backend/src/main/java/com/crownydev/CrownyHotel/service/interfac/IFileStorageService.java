package com.crownydev.CrownyHotel.service.interfac;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {

    /**
     * Saves a file (e.g., an image) to the storage.
     *
     * @param file The MultipartFile to be saved.
     * @return A unique identifier for the saved file (e.g., a generated filename).
     */
    String saveFile(MultipartFile file);

    /**
     * Retrieves a file from storage as a byte array.
     *
     * @param fileIdentifier The unique identifier of the file to retrieve.
     * @return The file's content as a byte array.
     */
    byte[] downloadFile(String fileIdentifier);

    /**
     * Deletes a file from storage.
     *
     * @param fileIdentifier The unique identifier of the file to delete.
     */
    void deleteFile(String fileIdentifier);
}