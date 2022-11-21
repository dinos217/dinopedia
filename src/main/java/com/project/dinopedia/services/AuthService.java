package com.project.dinopedia.services;

import com.project.dinopedia.dtos.LoginRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    void authenticate(LoginRequestDto loginRequestDto) throws Exception;
}
