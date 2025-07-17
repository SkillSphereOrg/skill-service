package com.skillsphere.skill_service.controller;

import com.skillsphere.skill_service.model.PracticeLog;
import com.skillsphere.skill_service.service.PracticeLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/practice-logs")
@RequiredArgsConstructor
public class PracticeLogController {
    private final PracticeLogService practiceLogService;
    private static final Logger log = LoggerFactory.getLogger(PracticeLogController.class);

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public List<PracticeLog> getUserLogs(@PathVariable Long userId) {
        log.info("Fetching practice logs for userId: {}", userId);
        return practiceLogService.getUserLogs(userId);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PracticeLog> logPractice(@RequestBody PracticeLog practiceLog) {
        log.info("Logging practice for userId: {} skillId: {}", practiceLog.getUserId(),
                practiceLog.getSkill().getId());
        return ResponseEntity.ok(practiceLogService.logPractice(practiceLog));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PracticeLog> updatePractice(@PathVariable Long id, @RequestBody PracticeLog practiceLog) {
        log.info("Updating practice log id: {}", id);
        return ResponseEntity.ok(practiceLogService.updatePractice(id, practiceLog));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletePractice(@PathVariable Long id) {
        log.info("Deleting practice log id: {}", id);
        practiceLogService.deletePractice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/range")
    @PreAuthorize("isAuthenticated()")
    public List<PracticeLog> getUserLogsByDateRange(@PathVariable Long userId, @RequestParam String start,
            @RequestParam String end) {
        log.info("Fetching practice logs for userId: {} from {} to {}", userId, start, end);
        return practiceLogService.getUserLogsByDateRange(userId, LocalDate.parse(start), LocalDate.parse(end));
    }

    @GetMapping("/user/{userId}/total-minutes")
    @PreAuthorize("isAuthenticated()")
    public int getTotalMinutesByUserAndDateRange(@PathVariable Long userId, @RequestParam String start,
            @RequestParam String end) {
        log.info("Calculating total minutes for userId: {} from {} to {}", userId, start, end);
        return practiceLogService.getTotalMinutesByUserAndDateRange(userId, LocalDate.parse(start),
                LocalDate.parse(end));
    }

    @GetMapping("/user/{userId}/streak")
    @PreAuthorize("isAuthenticated()")
    public int getStreak(@PathVariable Long userId) {
        log.info("Calculating streak for userId: {}", userId);
        return practiceLogService.getStreak(userId);
    }
}