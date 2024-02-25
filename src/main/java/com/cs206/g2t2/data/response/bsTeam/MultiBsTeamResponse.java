package com.cs206.g2t2.data.response.bsTeam;

import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.models.BsTeam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultiBsTeamResponse implements Response {
    protected List<BsTeam> bsTeams;
}
