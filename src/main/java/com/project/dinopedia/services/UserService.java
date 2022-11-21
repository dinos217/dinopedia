package com.project.dinopedia.services;

import com.project.dinopedia.dtos.DinosaurDto;
import com.project.dinopedia.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDto findByUsername(String email);

    UserDto save(UserDto user);

    UserDto findById(Long userId);

    List<DinosaurDto> findUserFavourites(String username);
}
