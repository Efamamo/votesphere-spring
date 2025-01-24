package com.itsc.votesphere.email;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OTPGenerator {

    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
