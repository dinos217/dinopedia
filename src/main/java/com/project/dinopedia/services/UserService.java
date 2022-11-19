package com.project.dinopedia.services;

import com.project.dinopedia.dtos.DinosaurDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<DinosaurDto> findUserFavourites(String username);
}
