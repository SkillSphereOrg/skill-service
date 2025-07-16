package com.skillsphere.skill_service.controller;

import com.skillsphere.skill_service.model.UserSkill;
import com.skillsphere.skill_service.service.UserSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-skills")
@RequiredArgsConstructor
public class UserSkillController {
    private final UserSkillService userSkillService;

    @GetMapping("/user/{userId}")
    public List<UserSkill> getUserSkills(@PathVariable Long userId) {
        return userSkillService.getUserSkills(userId);
    }

    @PostMapping
    public ResponseEntity<UserSkill> assignSkillToUser(@RequestBody UserSkill userSkill) {
        return ResponseEntity.ok(userSkillService.assignSkillToUser(userSkill));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSkill> updateUserSkill(@PathVariable Long id, @RequestBody UserSkill userSkill) {
        return ResponseEntity.ok(userSkillService.updateUserSkill(id, userSkill));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUserSkill(@PathVariable Long id) {
        userSkillService.removeUserSkill(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/skill/{skillId}")
    public List<UserSkill> getSkillsBySkillId(@PathVariable Long skillId) {
        return userSkillService.getSkillsBySkillId(skillId);
    }
}