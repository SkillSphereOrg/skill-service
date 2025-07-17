package com.skillsphere.skill_service.config;

import com.skillsphere.skill_service.model.Skill;
import com.skillsphere.skill_service.model.PracticeLog;
import com.skillsphere.skill_service.repository.SkillRepository;
import com.skillsphere.skill_service.repository.PracticeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Instant;
import java.util.List;

@Component
@Profile({ "dev", "default" })
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private PracticeLogRepository practiceLogRepository;

    @Override
    public void run(String... args) {
        if (skillRepository.count() == 0) {
            Skill java = skillRepository
                    .save(Skill.builder().name("Java").description("Java programming").category("Tech").build());
            Skill react = skillRepository
                    .save(Skill.builder().name("React").description("Frontend framework").category("Tech").build());
            Skill writing = skillRepository.save(
                    Skill.builder().name("Writing").description("Creative writing").category("Soft Skill").build());
            // Optionally add sample practice logs
            practiceLogRepository.saveAll(List.of(
                    PracticeLog.builder().userId(1L).skill(java).date(LocalDate.now().minusDays(1)).duration(60)
                            .notes("Practiced OOP").tags("revision").createdAt(Instant.now()).build(),
                    PracticeLog.builder().userId(1L).skill(react).date(LocalDate.now()).duration(45)
                            .notes("Hooks practice").tags("deep work").createdAt(Instant.now()).build()));
        }
    }
}