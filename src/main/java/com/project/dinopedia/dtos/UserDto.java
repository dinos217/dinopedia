package com.project.dinopedia.dtos;

import com.project.dinopedia.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private LocalDateTime dateCreated;
    private List<Role> roles;
    private List<VoteDto> likes;
}
