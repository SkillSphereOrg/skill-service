package com.skillsphere.skill_service.repository;

import com.skillsphere.skillservice.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findByName(String name);

    List<Skill> findByCategory(String category);
}