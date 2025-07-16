package com.skillsphere.skill_service.service;

import com.skillsphere.skill_service.model.Skill;
import com.skillsphere.skill_service.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Optional<Skill> getSkillById(Long id) {
        return skillRepository.findById(id);
    }

    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, Skill updated) {
        Skill skill = skillRepository.findById(id).orElseThrow(() -> new RuntimeException("Skill not found"));
        skill.setName(updated.getName());
        skill.setDescription(updated.getDescription());
        skill.setCategory(updated.getCategory());
        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }

    public List<Skill> findByCategory(String category) {
        return skillRepository.findByCategory(category);
    }

    public Optional<Skill> findByName(String name) {
        return skillRepository.findByName(name);
    }
}