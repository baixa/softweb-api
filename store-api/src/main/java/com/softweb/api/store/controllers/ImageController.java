package com.softweb.api.store.controllers;

import com.softweb.api.store.model.entities.Image;
import com.softweb.api.store.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> getImageById (@PathVariable(name = "id") String imageId) {
        return ResponseEntity.ok(imageService.getImageById(imageId));
    }

    @GetMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> getImages () {
        return ResponseEntity.ok(imageService.getImages());
    }

    @PostMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> postImage (@RequestBody Image image) {
        imageService.saveImage(image);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> putImage (@RequestBody Image image) {
        imageService.saveImage(image);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> deleteImage (@PathVariable(name = "id") String imageId) {
        imageService.deleteImageById(imageId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
