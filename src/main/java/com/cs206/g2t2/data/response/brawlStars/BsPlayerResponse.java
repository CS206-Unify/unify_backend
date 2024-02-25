package com.cs206.g2t2.data.response.brawlStars;

import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.models.brawlStarsAPI.playerInfo.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BsPlayerResponse implements Response {
    protected Player player;
}
