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
public class TeamUpdateRequest {

    @NotBlank(message = "Team Name is required")
    @Size(min = 8,
            max = 30,
            message = "Team Name must be between {min} and {max} characters long")
    private String teamName;

    //imageString stores the S3 image link, can be null if there is no image
    private String imageString;

    // trophyRequirements stores the number of trophies that user need before joining the team
    @Min(value = 0, message = "Trophy Requirements has a minimum value of {value}")
    private int trophyRequirements;

    // min3v3Wins stores the number of wins in 3v3 game mode that user needs before joining the team
    @Min(value = 0, message = "Min 3v3 Wins has a minimum value of {value}")
    private int min3v3Wins;

    // minDuoWins stores the number of wins in 2v2 game mode that user needs before joining the team
    @Min(value = 0, message = "Min Duo Wins has a minimum value of {value}")
    private int minDuoWins;

    // minSoloWins stores the number of wins in 1v1 game mode that user needs before joining the team
    @Min(value = 0, message = "Min Solo Wins has a minimum value of {value}")
    private int minSoloWins;
}

