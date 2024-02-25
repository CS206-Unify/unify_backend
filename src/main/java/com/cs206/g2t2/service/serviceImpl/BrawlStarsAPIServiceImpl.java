package com.cs206.g2t2.service.serviceImpl;

import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.data.response.brawlStars.BsPlayerResponse;
import com.cs206.g2t2.data.response.common.SuccessResponse;
import com.cs206.g2t2.exceptions.notFound.BsPlayerTagNotFoundException;
import com.cs206.g2t2.exceptions.notFound.UsernameNotFoundException;
import com.cs206.g2t2.models.User;
import com.cs206.g2t2.models.brawlStarsAPI.playerInfo.Player;
import com.cs206.g2t2.repository.UserRepository;
import com.cs206.g2t2.service.services.BrawlStarsAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class BrawlStarsAPIServiceImpl implements BrawlStarsAPIService {

    private static final String brawlStarsInstanceUrl = "https://api.brawlstars.com/v1";

    private static final String playerInfoPath = "/players/%23";
    private static final String battleLogUrl = "/battlelog";

    @Value("${unify.backend.app.brawlStarsKey}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    private final UserRepository userRepository;

    private String getPlayerTagFromUser(String username) {
        //Obtain user from userRepository and throws UsernameNotFoundException if user not found
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Obtain playerTag of user from user
        String playerTag = user.getBsProfile().getPlayerTag();

        //Throws BsPlayerTagNotFoundException if no playerTag found
        if (playerTag == null) { throw new BsPlayerTagNotFoundException(username); }

        //Return user's BS playerTag
        return playerTag;
    }

    public Player findPlayerFromUsername(String username) throws UsernameNotFoundException, BsPlayerTagNotFoundException {
        //Obtains playerTag from username
        String playerTag = getPlayerTagFromUser(username);

        //Stores the ResponseEntity of the API call
        ResponseEntity<Player> responseEntity = null;

        try {
            //Sets headers values of request and constructs a HttpEntity
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.set("Accept", "application/json");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            //Constructs the URI for the API call to obtain playerInfo
            URI uri = URI.create(brawlStarsInstanceUrl + playerInfoPath + playerTag);

            //Make a GET call to the API
            responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    Player.class
            );

        } catch (Exception e) {
            //Access what caused the exception and throw appropriate exception
            throw new RuntimeException(e.getMessage());
        }

        //Returns Player in ResponseEntity
        return responseEntity.getBody();
    }


    @Override
    public Response getBsStats(String username) throws UsernameNotFoundException, BsPlayerTagNotFoundException {
        //Finds the Brawl Stars player from username
        Player player = findPlayerFromUsername(username);

        //Return a BsPlayerResponse
        return BsPlayerResponse.builder()
                .player(player)
                .build();
    }

    @Override
    public Response updateBsStats(String username) {
        //Finds the Brawl Stars player from username
        Player player = findPlayerFromUsername(username);

        //Obtain user from userRepository and throws UsernameNotFoundException if user not found
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Obtains user from BsProfile
        user.getBsProfile().setPlayer(player);

        //Saves user back into userRepository
        userRepository.save(user);

        //Returns SuccessResponse once the Stats has been updated
        return SuccessResponse.builder()
                .message("User's Brawl Star Stats has been updated successfully")
                .build();
    }
}
