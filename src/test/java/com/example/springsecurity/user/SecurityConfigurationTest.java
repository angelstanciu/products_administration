package com.example.springsecurity.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityConfigurationTest {

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void loadPasswordEncripted(){
        System.out.println(encoder.encode("user"));
        System.out.println(encoder.encode("admin"));
    }
}
