package com.skillsphere.skill_service.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a recommendation for a user, such as a skill suggestion or practice tip.")
public class RecommendationDto {
    @Schema(description = "Type of recommendation", example = "SKILL_SUGGESTION")
    private String type;

    @Schema(description = "Title of the recommendation", example = "Try learning: React")
    private String title;

    @Schema(description = "Detailed description of the recommendation", example = "Based on your interest in web development, you might enjoy learning React.")
    private String description;

    @Schema(description = "Optional ID of the skill this recommendation relates to", example = "2")
    private Long relatedSkillId;
}