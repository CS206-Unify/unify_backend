package com.cs206.g2t2.models.brawlStarsAPI.battleLog;

import com.cs206.g2t2.data.response.brawlStars.Paging;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class BattleLogList {
    @SerializedName("items")
    private List<BattleLog> items;

    @SerializedName("paging")
    protected Paging paging;
}
