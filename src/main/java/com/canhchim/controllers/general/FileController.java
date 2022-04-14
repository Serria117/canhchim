package com.canhchim.controllers.general;

import com.canhchim.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = "/images")
@CrossOrigin
public class FileController
{
    @Autowired
    FileStorageService fileStorageService;

    @PostMapping("upload")
    public List<String> upload(@RequestParam("files") MultipartFile[] files)
    {
        try {
            List<String> fileName = new ArrayList<>();
            Arrays.asList(files).forEach(file -> fileName.add(fileStorageService.storeFile(file)));
            return fileName;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This API resolve URL for file
     *
     * @param filename
     * @return file
     */
    @GetMapping("{filename:.+}")
    public ResponseEntity<Resource> readFile(@PathVariable("filename") String filename)
    {
        Resource file = fileStorageService.read(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }
}
