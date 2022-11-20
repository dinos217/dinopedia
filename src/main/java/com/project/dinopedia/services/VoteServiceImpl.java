package com.project.dinopedia.services;

import com.project.dinopedia.dtos.VoteDto;
import com.project.dinopedia.dtos.VoteRequestDto;
import com.project.dinopedia.entities.Dinosaur;
import com.project.dinopedia.entities.User;
import com.project.dinopedia.entities.Vote;
import com.project.dinopedia.exceptions.BadRequestException;
import com.project.dinopedia.exceptions.InvalidRequestException;
import com.project.dinopedia.repositories.DinosaurRepository;
import com.project.dinopedia.repositories.UserRepository;
import com.project.dinopedia.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {

    private UserRepository userRepository;
    private DinosaurRepository dinosaurRepository;
    private VoteRepository voteRepository;

    @Autowired
    public VoteServiceImpl(UserRepository userRepository, DinosaurRepository dinosaurRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.dinosaurRepository = dinosaurRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public VoteDto save(VoteRequestDto voteRequestDto) {

        User user = userRepository.findById(voteRequestDto.getUserId())
                .orElseThrow(() -> new BadRequestException("User with id " + voteRequestDto.getUserId() + " does not exist"));

        Dinosaur dinosaur = dinosaurRepository.findById(voteRequestDto.getDinosaurId())
                .orElseThrow(() -> new BadRequestException("Dinosaur with id " + voteRequestDto.getDinosaurId() + " does not exist"));

        if (!voteRepository.existsByUserAndDinosaur(user, dinosaur)) {
            Vote vote = new Vote();
            vote.setUser(user);
            vote.setDinosaur(dinosaur);
            vote.setLike(voteRequestDto.getLike());

            return buildVoteDto(voteRepository.save(vote));
        } else {
            throw new InvalidRequestException("User " + user.getUsername() + " has already liked this dinosaur");
        }
    }

    private VoteDto buildVoteDto(Vote vote) {

        VoteDto voteDto = new VoteDto();
        voteDto.setId(vote.getId());
        voteDto.setDinosaurName(vote.getDinosaur().getName());
        voteDto.setUsername(vote.getUser().getUsername());
        return voteDto;
    }
}
