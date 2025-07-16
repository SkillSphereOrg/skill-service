package com.skillsphere.skill_service.service;

import com.skillsphere.skill_service.model.UserSkill;
import com.skillsphere.skill_service.model.Skill;
import com.skillsphere.skill_service.repository.UserSkillRepository;
import com.skillsphere.skill_service.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSkillService {
    private final UserSkillRepository userSkillRepository;
    private final SkillRepository skillRepository;

    public List<UserSkill> getUserSkills(Long userId) {
        return userSkillRepository.findByUserId(userId);
    }

    public UserSkill assignSkillToUser(UserSkill userSkill) {
        userSkill.setLastUpdated(Instant.now());
        return userSkillRepository.save(userSkill);
    }

    public UserSkill updateUserSkill(Long id, UserSkill updated) {
        UserSkill userSkill = userSkillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserSkill not found"));
        userSkill.setLevel(updated.getLevel());
        userSkill.setEndorsements(updated.getEndorsements());
        userSkill.setLastUpdated(Instant.now());
        return userSkillRepository.save(userSkill);
    }

    public void removeUserSkill(Long id) {
        userSkillRepository.deleteById(id);
    }

    public List<UserSkill> getSkillsBySkillId(Long skillId) {
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"));
        return userSkillRepository.findBySkill(skill);
    }
}