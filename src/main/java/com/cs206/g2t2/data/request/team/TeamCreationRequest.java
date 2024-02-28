package com.cs206.g2t2.data.request.team;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamCreationRequest {

    // teamName stores the username to be registered into the repository
    @NotBlank(message = "Team Name is required")
    @Size(min = 8,
            max = 30,
            message = "Team Name must be between {min} and {max} characters long")
    private String teamName;

    // region stores the competing region of the team
    @NotBlank(message = "Region is required")
    private String region;

    // language stores the competing language of the team
    @NotBlank(message = "Language is required")
    private String language;

    // maximumTeamSize stores the maximum capacity of the team
    @Min(value = 3, message = "Maximum Team Size must be more than or equal to {value}")
    private int maximumTeamSize;
}
