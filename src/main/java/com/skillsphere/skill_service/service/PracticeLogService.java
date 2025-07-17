package com.skillsphere.skill_service.service;

import com.skillsphere.skill_service.model.PracticeLog;
import com.skillsphere.skill_service.model.Skill;
import com.skillsphere.skill_service.repository.PracticeLogRepository;
import com.skillsphere.skill_service.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PracticeLogService {
    private final PracticeLogRepository practiceLogRepository;
    private final SkillRepository skillRepository;

    public PracticeLog logPractice(PracticeLog log) {
        log.setCreatedAt(Instant.now());
        return practiceLogRepository.save(log);
    }

    public PracticeLog updatePractice(Long id, PracticeLog updated) {
        PracticeLog log = practiceLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PracticeLog not found"));
        log.setDate(updated.getDate());
        log.setDuration(updated.getDuration());
        log.setNotes(updated.getNotes());
        log.setTags(updated.getTags());
        return practiceLogRepository.save(log);
    }

    public void deletePractice(Long id) {
        practiceLogRepository.deleteById(id);
    }

    public List<PracticeLog> getUserLogs(Long userId) {
        return practiceLogRepository.findByUserId(userId);
    }

    public List<PracticeLog> getSkillLogs(Long skillId) {
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"));
        return practiceLogRepository.findBySkill(skill);
    }

    public List<PracticeLog> getUserLogsByDateRange(Long userId, LocalDate start, LocalDate end) {
        return practiceLogRepository.findByUserIdAndDateBetween(userId, start, end);
    }

    public int getTotalMinutesByUserAndDateRange(Long userId, LocalDate start, LocalDate end) {
        return getUserLogsByDateRange(userId, start, end).stream().mapToInt(PracticeLog::getDuration).sum();
    }

    public int getStreak(Long userId) {
        List<PracticeLog> logs = practiceLogRepository.findByUserId(userId);
        return calculateStreak(logs);
    }

    private int calculateStreak(List<PracticeLog> logs) {
        if (logs.isEmpty())
            return 0;
        logs.sort(Comparator.comparing(PracticeLog::getDate).reversed());
        int streak = 0;
        LocalDate today = LocalDate.now();
        for (PracticeLog log : logs) {
            if (log.getDate().equals(today.minusDays(streak))) {
                streak++;
            } else {
                break;
            }
        }
        return streak;
    }
}