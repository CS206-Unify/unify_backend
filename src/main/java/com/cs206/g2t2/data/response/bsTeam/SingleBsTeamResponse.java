package com.cs206.g2t2.data.response.bsTeam;

import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.models.team.BsTeam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleBsTeamResponse implements Response {
    protected BsTeam bsTeam;
}
