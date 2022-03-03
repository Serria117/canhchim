package com.canhchim.services;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileStorageService
{
    private final Path uploadPath = Path.of("upload");
    Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    //Create folder to upload files to:
    public FileStorageService()
    {
        try {
            Files.createDirectory(this.uploadPath);
        } catch (IOException e) {
            logger.error("Error creating directory. " + e.getMessage());
        }
    }

    //Check the file's extension for image
    private boolean isImage(MultipartFile file)
    {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename()); //FilenameUtils from apache.commons.io dependency
        assert ext != null;
        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "gif"}).contains(ext.trim().toLowerCase());
    }

    //Create an unique file name to avoid overriding file with the same name:
    private String uniqueFileName(MultipartFile file)
    {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        return UUID.randomUUID() + "." + ext;
    }

    //Store the file:
    public String storeFile(MultipartFile file)
    {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty. Nothing to store.");
        }
        if (isImage(file)) {
            try {
                String newFileName = uniqueFileName(file);
                Files.copy(
                        file.getInputStream(),
                        this.uploadPath.resolve(newFileName).normalize().toAbsolutePath());
                return newFileName;
            } catch (IOException e) {
                throw new RuntimeException("Upload failed." + e.getMessage());
            }
        } else {
            throw new RuntimeException("Upload failed. File type is not a valid image type.");
        }
    }

    //read single file:
    public Resource read(String fileName)
    {
        Path file = uploadPath.resolve(fileName);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file. File does not exist or inaccessible.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }

    //load list of file:
    public Stream<Path> loadList()
    {
        try {
            return Files.walk(this.uploadPath, 1)
                    .filter(path -> !path.equals(this.uploadPath))
                    .map(this.uploadPath::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Error: Could not load files list. " + e.getMessage());
        }
    }

    //Delete file:
    public boolean delete(String filename)
    {
        try {
            Path file = uploadPath.resolve(filename);
            FileUtils.delete(file.toFile());
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
