package com.cs206.g2t2.service.serviceImpl;

import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.data.response.bsTeam.MultiBsTeamListingResponse;
import com.cs206.g2t2.data.response.user.MultiBsProfileListingResponse;
import com.cs206.g2t2.exceptions.notFound.UsernameNotFoundException;
import com.cs206.g2t2.models.User;
import com.cs206.g2t2.models.profile.BsProfileListing;
import com.cs206.g2t2.models.team.BsTeam;
import com.cs206.g2t2.models.team.BsTeamListing;
import com.cs206.g2t2.repository.UserRepository;
import com.cs206.g2t2.service.services.DiscoverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscoverServiceImpl implements DiscoverService {

    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;

    @Override
    public Response discoverBsTeam(String username, String region, String language, Integer trophies,
                                 Integer threeVThreeWins, Integer twoVTwoWins, Integer soloWins,
                                 Integer pageSize, Integer pageNumber) {
        //Find user from repository
        User profile = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Create Pageable and Query
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Query query = new Query().with(pageable);

        //Setting Criteria to filter repository
        //Removes searches for teams that user is already in
        if (profile.getTeams().size() != 0) {
            Criteria criteria = Criteria.where("_id").ne(profile.getTeams().get(0));
            for (int i = 1 ; i < profile.getTeams().size() ; i++) {
                criteria.and(profile.getTeams().get(i));
            }
            query.addCriteria(criteria);
        }

        //If region is not null or "Any", apply criteria to find results that MATCH region
        if (region != null && !region.equals("Any")) {
            query.addCriteria(Criteria.where("region").is(region));
        }
        //If language is not null or "Any", apply criteria to find results that MATCH language
        if (language != null && !language.equals("Any")) {
            query.addCriteria(Criteria.where("language").is(language));
        }
        //If trophies is not null, apply criteria to find results that trophyRequirements that are less than or equal to trophies
        if (trophies != null) {
            query.addCriteria(Criteria.where("trophyRequirements").lte(trophies.intValue()));
        }
        //If threeVThreeWins is not null, apply criteria to find results that min3v3Wins that are less than or equal to threeVThreeWins
        if (threeVThreeWins != null) {
            query.addCriteria(Criteria.where("min3v3Wins").lte(threeVThreeWins.intValue()));
        }
        //If twoVTwoWins is not null, apply criteria to find results that twoVTwoWins that are less than or equal to twoVTwoWins
        if (twoVTwoWins != null) {
            query.addCriteria(Criteria.where("minDuoWins").lte(twoVTwoWins.intValue()));
        }
        //If soloWins is not null, apply criteria to find results that minSoloWins that are less than or equal to soloWins
        if (soloWins != null) {
            query.addCriteria(Criteria.where("minSoloWins").lte(soloWins.intValue()));
        }

        //Sorting the results of the filtered search
//        query.with(Sort.by(Sort.Direction.DESC, "trophyRequirements"));
//        query.with(Sort.by(Sort.Direction.DESC, "min3v3Wins"));
//        query.with(Sort.by(Sort.Direction.DESC, "minDuoWins"));
//        query.with(Sort.by(Sort.Direction.DESC, "minSoloWins"));
//        query.with(Sort.by(Sort.Direction.ASC, "region"));
//        query.with(Sort.by(Sort.Direction.ASC, "language"));

        //Generate list of outputs
        List<BsTeam> bsTeamList = mongoTemplate.find(query, BsTeam.class);

        //Convert output into List<BsTeamListing>
        List<BsTeamListing> bsTeamListings = new ArrayList<>();
        for (BsTeam bsTeam : bsTeamList) {
            bsTeamListings.add(new BsTeamListing(bsTeam));
        }

        //Generate Response
        return MultiBsTeamListingResponse.builder()
                .bsTeamListings(bsTeamListings)
                .build();
    }

    @Override
    public Response discoverBsProfile(String username, String region, String language, Integer trophies,
                                    Integer threeVThreeWins, Integer twoVTwoWins, Integer soloWins,
                                    Integer pageSize, Integer pageNumber) {
        //Find user from repository
        User profile = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Create Pageable and Query
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Query query = new Query().with(pageable);

        //Setting Criteria to filter repository
        //Set condition that playerTag must not be null
        query.addCriteria(Criteria.where("bsProfile.playerTag").ne(null));
        //Set condition that user cannot find himself
        query.addCriteria(Criteria.where("_id").ne(profile.get_id()));

        //If region is not null or "Any", apply criteria to find results that MATCH region
        if (region != null && !region.equals("Any")) {
            query.addCriteria(Criteria.where("bsProfile.region").is(region));
        }
        //If language is not null or "Any", apply criteria to find results that MATCH language
        if (language != null && !language.equals("Any")) {
            query.addCriteria(Criteria.where("language").is(language));
        }
        //If trophies is not null, apply criteria to find results that trophyRequirements that are greater than or equal to trophies
        if (trophies != null) {
            query.addCriteria(Criteria.where("bsProfile.player.trophies").gte(trophies.intValue()));
        }
        //If threeVThreeWins is not null, apply criteria to find results that threeVsThreeVictories that are greater than or equal to threeVThreeWins
        if (threeVThreeWins != null) {
            query.addCriteria(Criteria.where("bsProfile.player.threeVsThreeVictories").gte(threeVThreeWins.intValue()));
        }
        //If twoVTwoWins is not null, apply criteria to find results that duoVictories that are greater than or equal to twoVTwoWins
        if (twoVTwoWins != null) {
            query.addCriteria(Criteria.where("bsProfile.player.duoVictories").gte(twoVTwoWins.intValue()));
        }
        //If soloWins is not null, apply criteria to find results that soloVictories that are greater than or equal to soloWins
        if (soloWins != null) {
            query.addCriteria(Criteria.where("bsProfile.player.soloVictories").gte(soloWins.intValue()));
        }

        //Sorting the results of the filtered search
//        query.with(Sort.by(Sort.Direction.ASC, "bsProfile.player.trophies"));
//        query.with(Sort.by(Sort.Direction.ASC, "bsProfile.player.threeVsThreeVictories"));
//        query.with(Sort.by(Sort.Direction.ASC, "bsProfile.player.duoVictories"));
//        query.with(Sort.by(Sort.Direction.ASC, "bsProfile.player.soloVictories"));
//        query.with(Sort.by(Sort.Direction.ASC, "bsProfile.region"));
//        query.with(Sort.by(Sort.Direction.ASC, "language"));

        //Generate list of outputs
        List<User> userList = mongoTemplate.find(query, User.class);

        //Convert output into List<BsProfileListing>
        List<BsProfileListing> bsProfileListingList = new ArrayList<>();
        for (User user : userList) {
            bsProfileListingList.add(new BsProfileListing(user));
        }

        //Generate Response
        return MultiBsProfileListingResponse.builder()
                .bsProfileListingList(bsProfileListingList)
                .build();
    }
}
