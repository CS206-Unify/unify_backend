package com.cs206.g2t2.data.response.bsTeam;

import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.models.team.BsTeam;
import com.cs206.g2t2.models.team.BsTeamListing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultiBsTeamListingResponse implements Response {
    protected List<BsTeamListing> bsTeamListings;
}
