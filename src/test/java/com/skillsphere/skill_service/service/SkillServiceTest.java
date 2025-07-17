package com.skillsphere.skill_service.service;

import com.skillsphere.skill_service.model.Skill;
import com.skillsphere.skill_service.repository.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SkillServiceTest {
    @Mock
    private SkillRepository skillRepository;
    @InjectMocks
    private SkillService skillService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAndGetSkill() {
        Skill skill = Skill.builder().name("Java").build();
        when(skillRepository.save(any())).thenReturn(skill);
        Skill created = skillService.createSkill(skill);
        assertEquals("Java", created.getName());
    }

    @Test
    void getSkillById() {
        Skill skill = Skill.builder().name("Spring").build();
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
        Optional<Skill> found = skillService.getSkillById(1L);
        assertTrue(found.isPresent());
        assertEquals("Spring", found.get().getName());
    }

    @Test
    void getAllSkills() {
        when(skillRepository.findAll()).thenReturn(List.of(new Skill(), new Skill()));
        assertEquals(2, skillService.getAllSkills().size());
    }
}