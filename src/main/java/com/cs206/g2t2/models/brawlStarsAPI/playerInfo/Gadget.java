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
public class Gadget {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;
}
