package com.project.dinopedia.controllers;

import com.project.dinopedia.services.DinosaurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dinosaur")
@Slf4j
public class DinosaurController {

    private DinosaurService dinosaurService;

    @Autowired
    public DinosaurController(DinosaurService dinosaurService) {
        this.dinosaurService = dinosaurService;
    }
}
