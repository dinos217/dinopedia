package com.project.dinopedia.services;

import com.project.dinopedia.dtos.DinosaurDto;
import com.project.dinopedia.dtos.UserDto;
import com.project.dinopedia.entities.Dinosaur;
import com.project.dinopedia.entities.User;
import com.project.dinopedia.entities.Vote;
import com.project.dinopedia.exceptions.BadRequestException;
import com.project.dinopedia.exceptions.InvalidRequestException;
import com.project.dinopedia.mappers.DinosaurMapper;
import com.project.dinopedia.mappers.UserMapper;
import com.project.dinopedia.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private DinosaurMapper dinosaurMapper = Mappers.getMapper(DinosaurMapper.class);
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException("User not found."));
        return userMapper.userToUserDto(user);
    }

    @Transactional
    @Override
    public UserDto save(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new InvalidRequestException("Username " + userDto.getUsername() + " already exists.");
        }
        User user = userMapper.userDtoToUser(userDto);
        user.setDateCreated(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User result = userRepository.save(user);
        log.info("User " + result.getUsername() + " was created successfully");
        return userMapper.userToUserDto(result);
    }

    @Override
    public UserDto findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BadRequestException("User not found."));
        return userMapper.userToUserDto(user);
    }

    @Transactional
    @Override
    public List<DinosaurDto> findUserFavourites(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User " + username + " does not exist in database"));

        if (!CollectionUtils.isEmpty(user.getLikes())) {
            List<Dinosaur> userFavourites = user.getLikes().stream().map(Vote::getDinosaur).toList();
            return userFavourites.stream().map(dinosaur -> dinosaurMapper.dinosaurToDinosaurDto(dinosaur)).toList();
        } else
            throw new BadRequestException("User " + username + " has not liked any dinosaurs yet");
    }
}
