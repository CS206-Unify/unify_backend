package com.cs206.g2t2.data.response.brawlStars;

import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.models.brawlStarsAPI.battleLog.BattleLog;
import com.cs206.g2t2.models.brawlStarsAPI.battleLog.BattleLogList;
import com.cs206.g2t2.models.brawlStarsAPI.playerInfo.Player;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BsPlayerBattleLogResponse implements Response {

    protected BattleLogList battleLogList;

}
