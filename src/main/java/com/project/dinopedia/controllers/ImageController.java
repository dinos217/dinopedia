package com.project.dinopedia.controllers;

import com.project.dinopedia.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/images")
@Slf4j
public class ImageController {

    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/add-to-dinosaur/{dinoId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<String> addImagesToDinosaur(@PathVariable Long dinoId, @RequestPart List<MultipartFile> images) {
        log.info("Started saving images to Dinosaur " + dinoId);
        imageService.addImagesToDinosaur(dinoId, images);
        return ResponseEntity.status(HttpStatus.OK).body("Image(s) were saved successfully");
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("Started deleting image...");
        imageService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Image was deleted successfully");
    }
}