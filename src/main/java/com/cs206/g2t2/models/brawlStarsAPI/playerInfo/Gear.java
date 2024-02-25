package com.cs206.g2t2.models.brawlStarsAPI.playerInfo;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Gear {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("level")
    private int level;
}
