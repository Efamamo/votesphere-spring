package com.itsc.votesphere.auth;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class OTP {

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "otp is required")
    private String otp;


}

