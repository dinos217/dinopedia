package com.project.dinopedia.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequestDto {

    private Long userId;
    private Long dinosaurId;
    private Boolean like;
}
