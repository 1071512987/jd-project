package com.hzau.college.pojo.dto;

import lombok.Data;

@Data
public class LoginDto {

    private String token;

    private Boolean isNewUser ;
}
