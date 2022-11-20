package com.project.dinopedia.controllers;

import com.project.dinopedia.dtos.DinosaurDto;
import com.project.dinopedia.dtos.DinosaurRequestDto;
import com.project.dinopedia.services.DinosaurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/dinosaur")
@Slf4j
public class DinosaurController {

    private DinosaurService dinosaurService;

    @Autowired
    public DinosaurController(DinosaurService dinosaurService) {
        this.dinosaurService = dinosaurService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<DinosaurDto> save(@RequestPart(name = "dinosaurRequestDto") DinosaurRequestDto dinosaurRequestDto,
                                     @RequestPart(name = "images", required = false) List<MultipartFile> images) {
        log.info("Started saving new Dinosaur...");
        return ResponseEntity.status(HttpStatus.OK).body(dinosaurService.save(dinosaurRequestDto, images));
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<DinosaurDto> update(@RequestBody DinosaurDto dinosaurDto) {
        log.info("Started updating Dinosaur...");
        return ResponseEntity.status(HttpStatus.OK).body(dinosaurService.update(dinosaurDto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("Started deleting dinosaur...");
        dinosaurService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Dinosaur was deleted successfully.");
    }

    @GetMapping(value = "/all")
    Page<DinosaurDto> findAll(@RequestParam Integer page,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortBy,
                              @RequestParam String direction) {

        log.info("Started finding all dinosaurs paged...");

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return dinosaurService.findAll(pageable);
    }

    @GetMapping(value = "/{name}/images")
    ResponseEntity<List<byte[]>> findDinosaurImages(@PathVariable String name) {
        log.info("Started finding images of " + name);
        return ResponseEntity.status(HttpStatus.OK).body(dinosaurService.getDinosaurImages(name));
    }

    @GetMapping(value = "/eating-classes")
    ResponseEntity<List<String>> getAllEatingClasses() {
        log.info("Started finding all dinosaur eating classes...");
        return ResponseEntity.status(HttpStatus.OK).body(dinosaurService.getDinosaurEatingClasses());
    }

    @GetMapping(value = "/sizes")
    ResponseEntity<List<String>> getAllSizes() {
        log.info("Started finding all dinosaur sizes...");
        return ResponseEntity.status(HttpStatus.OK).body(dinosaurService.getDinosaurSizes());
    }

    @GetMapping(value = "/periods")
    ResponseEntity<List<String>> getAllPeriods() {
        log.info("Started finding all dinosaur periods...");
        return ResponseEntity.status(HttpStatus.OK).body(dinosaurService.getDinosaurPeriods());
    }
}
