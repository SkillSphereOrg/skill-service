package com.skillsphere.skill_service.repository;

import com.skillsphere.skill_service.model.PracticeLog;
import com.skillsphere.skill_service.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PracticeLogRepository extends JpaRepository<PracticeLog, Long> {
    List<PracticeLog> findByUserId(Long userId);

    List<PracticeLog> findBySkill(Skill skill);

    List<PracticeLog> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);

    List<PracticeLog> findByUserIdAndTagsContaining(Long userId, String tag);

    List<PracticeLog> findByUserIdAndSkill_Id(Long userId, Long skillId);

    List<PracticeLog> findByUserIdAndSkill_IdAndDateBetween(Long userId, Long skillId, LocalDate start, LocalDate end);
}