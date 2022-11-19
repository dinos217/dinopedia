package com.project.dinopedia.controllers;

import com.project.dinopedia.dtos.VoteDto;
import com.project.dinopedia.dtos.VoteRequestDto;
import com.project.dinopedia.services.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vote")
@Slf4j
public class VoteController {

    private VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<VoteDto> save(VoteRequestDto voteRequestDto) {
        log.info("Started saving user's vote for dinosaur...");
        return ResponseEntity.status(HttpStatus.OK).body(voteService.save(voteRequestDto));
    }
}
