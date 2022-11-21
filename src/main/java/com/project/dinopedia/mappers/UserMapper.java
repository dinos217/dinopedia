package com.project.dinopedia.mappers;

import com.project.dinopedia.dtos.UserDto;
import com.project.dinopedia.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User userDtoToUser(UserDto userDto);
    UserDto userToUserDto(User user);
}
