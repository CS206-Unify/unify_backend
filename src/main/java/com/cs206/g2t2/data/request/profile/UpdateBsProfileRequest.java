package com.cs206.g2t2.data.request.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBsProfileRequest {

    //Contains the user's region of play
    @NotBlank(message = "Region is required")
    private String region;

    //Contains the user's personal bio of BS
    @NotBlank(message = "Personal Bio is required")
    private String personalBio;
}
