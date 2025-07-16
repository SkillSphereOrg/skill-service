package com.skillsphere.skill_service.controller;

import com.skillsphere.skill_service.model.UserSkill;
import com.skillsphere.skill_service.service.UserSkillService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/user-skills")
@RequiredArgsConstructor
public class UserSkillController {
    private final UserSkillService userSkillService;
    private static final Logger log = LoggerFactory.getLogger(UserSkillController.class);

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public List<UserSkill> getUserSkills(@PathVariable Long userId) {
        log.info("Fetching skills for userId: {}", userId);
        return userSkillService.getUserSkills(userId);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserSkill> assignSkillToUser(@Valid @RequestBody UserSkill userSkill) {
        log.info("Assigning skill {} to user {}", userSkill.getSkill().getId(), userSkill.getUserId());
        return ResponseEntity.ok(userSkillService.assignSkillToUser(userSkill));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserSkill> updateUserSkill(@PathVariable Long id, @Valid @RequestBody UserSkill userSkill) {
        log.info("Updating userSkill id: {}", id);
        return ResponseEntity.ok(userSkillService.updateUserSkill(id, userSkill));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> removeUserSkill(@PathVariable Long id) {
        log.info("Removing userSkill id: {}", id);
        userSkillService.removeUserSkill(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/skill/{skillId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserSkill> getSkillsBySkillId(@PathVariable Long skillId) {
        log.info("Fetching user skills for skillId: {}", skillId);
        return userSkillService.getSkillsBySkillId(skillId);
    }
}