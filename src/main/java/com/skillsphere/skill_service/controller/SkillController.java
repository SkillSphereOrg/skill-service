package com.skillsphere.skill_service.controller;

import com.skillsphere.skill_service.model.Skill;
import com.skillsphere.skill_service.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;
    private static final Logger log = LoggerFactory.getLogger(SkillController.class);

    @GetMapping
    public List<Skill> getAllSkills() {
        log.info("Fetching all skills");
        return skillService.getAllSkills();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
        log.info("Fetching skill by id: {}", id);
        return skillService.getSkillById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody Skill skill) {
        log.info("Creating new skill: {}", skill.getName());
        return ResponseEntity.ok(skillService.createSkill(skill));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @Valid @RequestBody Skill skill) {
        log.info("Updating skill id: {}", id);
        return ResponseEntity.ok(skillService.updateSkill(id, skill));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        log.info("Deleting skill id: {}", id);
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Skill> searchByCategory(@RequestParam String category) {
        log.info("Searching skills by category: {}", category);
        return skillService.findByCategory(category);
    }
}
