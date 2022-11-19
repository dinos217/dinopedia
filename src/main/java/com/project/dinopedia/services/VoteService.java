package com.project.dinopedia.services;

import com.project.dinopedia.dtos.VoteDto;
import com.project.dinopedia.dtos.VoteRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface VoteService {

    VoteDto save(VoteRequestDto voteRequestDto);
}
