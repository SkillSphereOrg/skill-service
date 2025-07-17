package com.skillsphere.skill_service.controller;

import com.skillsphere.skill_service.service.AdminAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/analytics")
@RequiredArgsConstructor
public class AdminAnalyticsController {
    private final AdminAnalyticsService analyticsService;
    private static final Logger log = LoggerFactory.getLogger(AdminAnalyticsController.class);

    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> getSummary() {
        log.info("Admin requested system summary analytics");
        return analyticsService.getSummary();
    }

    @GetMapping("/top-skills")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Map<String, Object>> getTopSkills(@RequestParam(defaultValue = "5") int limit) {
        log.info("Admin requested top skills by practice time, limit {}", limit);
        return analyticsService.getTopSkillsByPracticeTime(limit);
    }
}