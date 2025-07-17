package com.skillsphere.skill_service.service;

import com.skillsphere.skill_service.model.PracticeLog;
import com.skillsphere.skill_service.model.Skill;
import com.skillsphere.skill_service.repository.PracticeLogRepository;
import com.skillsphere.skill_service.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminAnalyticsService {
    private final PracticeLogRepository practiceLogRepository;
    private final SkillRepository skillRepository;

    public Map<String, Object> getSummary() {
        List<PracticeLog> logs = practiceLogRepository.findAll();
        long totalUsers = logs.stream().map(PracticeLog::getUserId).distinct().count();
        long totalSkills = skillRepository.count();
        long totalLogs = logs.size();
        int totalMinutes = logs.stream().mapToInt(PracticeLog::getDuration).sum();
        return Map.of(
                "totalUsers", totalUsers,
                "totalSkills", totalSkills,
                "totalPracticeLogs", totalLogs,
                "totalMinutes", totalMinutes);
    }

    public List<Map<String, Object>> getTopSkillsByPracticeTime(int limit) {
        List<PracticeLog> logs = practiceLogRepository.findAll();
        Map<String, Integer> skillMinutes = new HashMap<>();
        for (PracticeLog log : logs) {
            String skillName = log.getSkill() != null ? log.getSkill().getName() : "Unknown";
            skillMinutes.put(skillName, skillMinutes.getOrDefault(skillName, 0) + log.getDuration());
        }
        return skillMinutes.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .map(e -> Map.of("skill", e.getKey(), "minutes", e.getValue()))
                .collect(Collectors.toList());
    }
}