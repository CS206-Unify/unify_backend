package com.cs206.g2t2.models.brawlStarsAPI.playerInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Club {
    String tag;
    String name;
}
