package com.cs206.g2t2.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BSProfile {

    //Contains the user's region of BS play
    private String region;

    //Contains the user's personal bio of BS
    private String personalBio;

}
