package com.cs206.g2t2.data.response.user;

import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.models.profile.BsProfileListing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultiBsProfileListingResponse implements Response {
    protected List<BsProfileListing> bsProfileListingList;
}
