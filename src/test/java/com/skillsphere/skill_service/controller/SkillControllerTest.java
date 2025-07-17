package com.skillsphere.skill_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsphere.skill_service.model.Skill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SkillControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String TEST_JWT = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsInJvbGVzIjpbIlJPTEVfQURNSU4iXX0.2QwQw5QwQw5QwQw5QwQw5QwQw5QwQwQw5QwQw5QwQwQw";

    private RequestPostProcessor jwt() {
        return request -> {
            request.addHeader("Authorization", TEST_JWT);
            return request;
        };
    }

    @Test
    void getAllSkills_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/skills"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createSkill_Authorized() throws Exception {
        Skill skill = Skill.builder().name("Docker").build();
        mockMvc.perform(post("/api/skills")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(skill)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Docker"));
    }
}