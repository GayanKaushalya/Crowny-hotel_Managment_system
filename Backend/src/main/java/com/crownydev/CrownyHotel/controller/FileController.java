package com.crownydev.CrownyHotel.controller;

import com.crownydev.CrownyHotel.service.interfac.IFileStorageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files") // A dedicated endpoint for files
public class FileController {

    private final IFileStorageService fileStorageService;

    public FileController(IFileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        byte[] fileBytes = fileStorageService.downloadFile(filename);
        Resource resource = new ByteArrayResource(fileBytes);

        // This returns the image so it can be displayed in an <img> tag
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Or determine dynamically
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }
}