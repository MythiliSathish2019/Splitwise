package com.Mythili.Splitwise.Dto;

import com.Mythili.Splitwise.Model.Group;
import com.Mythili.Splitwise.Model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateUserRequestDto {
    private String name;
    private String email;
    private String password;

    public static User from(CreateUserRequestDto createUserRequestDto){
        User user=new User();
        user.setName(createUserRequestDto.getName());
        user.setEmail(createUserRequestDto.getEmail());
        user.setPassword(createUserRequestDto.getPassword());
        return user;
    }
}
