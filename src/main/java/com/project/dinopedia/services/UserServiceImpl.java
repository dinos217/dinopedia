package com.project.dinopedia.services;

import com.project.dinopedia.dtos.DinosaurDto;
import com.project.dinopedia.entities.Dinosaur;
import com.project.dinopedia.entities.User;
import com.project.dinopedia.entities.Vote;
import com.project.dinopedia.exceptions.BadRequestException;
import com.project.dinopedia.mappers.DinosaurMapper;
import com.project.dinopedia.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private DinosaurMapper dinosaurMapper = Mappers.getMapper(DinosaurMapper.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<DinosaurDto> findUserFavourites(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User " + username + " does not exist in database."));

        if (!CollectionUtils.isEmpty(user.getLikes())) {
            List<Dinosaur> userFavourites = user.getLikes().stream().map(Vote::getDinosaur).toList();
            return userFavourites.stream().map(dinosaur -> dinosaurMapper.dinosaurToDinosaurDto(dinosaur)).toList();
        } else
            throw new BadRequestException("User " + username + " has not liked any dinosaurs yet.");
    }
}
