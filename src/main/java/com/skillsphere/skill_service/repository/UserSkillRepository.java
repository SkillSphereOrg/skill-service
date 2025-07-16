package com.skillsphere.skill_service.repository;

import com.skillsphere.skillservice.model.UserSkill;
import com.skillsphere.skillservice.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    List<UserSkill> findByUserId(Long userId);

    List<UserSkill> findBySkill(Skill skill);
}