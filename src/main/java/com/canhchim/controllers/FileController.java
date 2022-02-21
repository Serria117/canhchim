package com.canhchim.controllers;

import com.canhchim.response.ResponseObject;
import com.canhchim.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = "/files")
@CrossOrigin
public class FileController {
    @Autowired
    FileStorageService fileStorageService;

    @PostMapping("upload")
    public ResponseEntity<ResponseObject> upload(@RequestParam("files") MultipartFile[] files) {
        try {
            List<String> fileName = new ArrayList<>();
            Arrays.asList(files).forEach(f -> fileName.add(fileStorageService.storeFile(f)));
            return ResponseEntity.ok().body(new ResponseObject(200, "OK", fileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseObject(417, "FAILED", e.getMessage()));
        }
    }

    @GetMapping("{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
        Resource file = fileStorageService.read(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }

}
