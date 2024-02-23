package com.cs206.g2t2.data.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest implements UserRequest {

    //imageString stores the S3 image link, can be null if there is no image
    private String imageString;

    //country stores the user's country of origin/base
    @NotBlank(message = "Country is required")
    private String country;

    //language stores the user's preferred language
    @NotBlank(message = "Language is required")
    private String language;
}

