package com.cs206.g2t2.data.request.team;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamUpdateRequest {

    //imageString stores the S3 image link, can be null if there is no image
    private String imageString;

    // trophyRequirements stores the number of trophies that user need before joining the team
    @NotBlank(message = "Trophy Requirements is required")
    @Min(value = 0, message = "Trophy Requirements has a minimum value of {value}")
    private Integer trophyRequirements;

    // min3v3Wins stores the number of wins in 3v3 game mode that user needs before joining the team
    @NotBlank(message = "Min 3v3 Wins is required")
    @Min(value = 0, message = "Min 3v3 Wins has a minimum value of {value}")
    private Integer min3v3Wins;

    // minDuoWins stores the number of wins in 2v2 game mode that user needs before joining the team
    @NotBlank(message = "Min Duo Wins is required")
    @Min(value = 0, message = "Min Duo Wins has a minimum value of {value}")
    private Integer minDuoWins;

    // minSoloWins stores the number of wins in 1v1 game mode that user needs before joining the team
    @NotBlank(message = "Min Solo Wins is required")
    @Min(value = 0, message = "Min Solo Wins has a minimum value of {value}")
    private Integer minSoloWins;
}
