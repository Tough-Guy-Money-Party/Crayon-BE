package com.yoyomo.domain.club.presentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
class ClubControllerTest {

    MockMvc mockMvc;

    @Value("${crayon.jwt.masterToken}")
    private String token;


}