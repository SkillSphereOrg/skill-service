package com.skillsphere.skill_service.service;

import com.skillsphere.skill_service.model.PracticeLog;
import com.skillsphere.skill_service.model.Skill;
import com.skillsphere.skill_service.model.dto.RecommendationDto;
import com.skillsphere.skill_service.repository.PracticeLogRepository;
import com.skillsphere.skill_service.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final SkillRepository skillRepository;
    private final PracticeLogRepository practiceLogRepository;
    private final PracticeLogService practiceLogService;

    public List<RecommendationDto> getRecommendationsForUser(Long userId) {
        List<RecommendationDto> recommendations = new ArrayList<>();

        // 1. Suggest a new skill to learn
        recommendations.add(suggestNewSkill(userId));

        // 2. Provide a practice tip based on streak
        recommendations.add(providePracticeTip(userId));

        return recommendations;
    }

    private RecommendationDto suggestNewSkill(Long userId) {
        List<PracticeLog> userLogs = practiceLogRepository.findByUserId(userId);
        Set<Long> practicedSkillIds = userLogs.stream()
                .map(log -> log.getSkill().getId())
                .collect(Collectors.toSet());

        List<Skill> allSkills = skillRepository.findAll();
        Skill suggestedSkill = allSkills.stream()
                .filter(skill -> !practicedSkillIds.contains(skill.getId()))
                .findFirst()
                .orElse(null);

        if (suggestedSkill != null) {
            return RecommendationDto.builder()
                    .type("SKILL_SUGGESTION")
                    .title("Try learning: " + suggestedSkill.getName())
                    .description("Based on your interests, you might enjoy learning " + suggestedSkill.getName() + ".")
                    .relatedSkillId(suggestedSkill.getId())
                    .build();
        }
        return RecommendationDto.builder()
                .type("EXPLORE_MORE_SKILLS")
                .title("You're a master!")
                .description("You've practiced all available skills. Consider adding new skills to track.")
                .build();
    }

    private RecommendationDto providePracticeTip(Long userId) {
        int streak = practiceLogService.getStreak(userId);
        if (streak > 2) {
            return RecommendationDto.builder()
                    .type("PRACTICE_TIP")
                    .title("Keep up the great work!")
                    .description("You're on a " + streak + "-day streak. Keep the momentum going!")
                    .build();
        } else {
            return RecommendationDto.builder()
                    .type("PRACTICE_TIP")
                    .title("Build a Daily Habit")
                    .description("Logging your practice daily helps build consistency. Try to start a new streak!")
                    .build();
        }
    }
}