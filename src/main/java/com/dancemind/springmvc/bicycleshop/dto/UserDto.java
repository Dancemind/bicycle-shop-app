package com.dancemind.springmvc.bicycleshop.dto;

import com.dancemind.springmvc.bicycleshop.validation.PasswordMatches;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class UserDto {
    @NotNull
    @NotEmpty(message = "Username is required")
    @Size(min = 2, max = 100, message = "Name should be from 2 to 100 characters long")
    private String username;

    @NotNull
    @NotEmpty(message = "Password is required")
    @Size(min = 2, max = 300, message = "Password is at least 2 symbols")
    private String password;
    private String matchingPassword;

}
