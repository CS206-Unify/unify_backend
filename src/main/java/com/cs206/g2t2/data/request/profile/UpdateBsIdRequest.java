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
public class UpdateBsIdRequest {
    //Contains the user's Brawl Stars ID
    @NotBlank(message = "Brawl Stars ID is required")
    private String brawlStarsId;
}
