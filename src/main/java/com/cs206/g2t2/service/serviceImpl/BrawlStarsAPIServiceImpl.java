package com.cs206.g2t2.service.serviceImpl;

import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.data.response.brawlStars.BsPlayerBattleLogResponse;
import com.cs206.g2t2.data.response.brawlStars.BsPlayerResponse;
import com.cs206.g2t2.data.response.common.SuccessResponse;
import com.cs206.g2t2.exceptions.notFound.BsPlayerTagNotFoundException;
import com.cs206.g2t2.exceptions.notFound.UsernameNotFoundException;
import com.cs206.g2t2.models.User;
import com.cs206.g2t2.models.brawlStarsAPI.battleLog.Battle;
import com.cs206.g2t2.models.brawlStarsAPI.battleLog.BattleLog;
import com.cs206.g2t2.models.brawlStarsAPI.battleLog.BattleLogList;
import com.cs206.g2t2.models.brawlStarsAPI.playerInfo.Player;
import com.cs206.g2t2.repository.UserRepository;
import com.cs206.g2t2.service.services.BrawlStarsAPIService;
import com.google.gson.Gson;
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
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class BrawlStarsAPIServiceImpl implements BrawlStarsAPIService {

    private static final String brawlStarsInstanceUrl = "https://api.brawlstars.com/v1";

    private static final String playerInfoPath = "/players/%23";
    
    private static final String battleLogPath = "/battlelog";

    @Value("${unify.backend.app.brawlStarsKey}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    private final UserRepository userRepository;
    private final Gson gson;

    /**
     * Private Helper: Perform API call to obtain Brawl Star Player Stats
     * If user is not found based on username, throw UsernameNotFoundException
     * If no player tag can be found under the username, throw BsPlayerTagNotFoundException
     *
     * @param username a String containing the username of the user obtained from the token
     * @return Player object containing user's player stats
     */
    private String getPlayerTagFromUser(String username) throws UsernameNotFoundException, BsPlayerTagNotFoundException {
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

    /**
     * Private Helper: Perform API call to obtain Brawl Star Player Stats
     *
     * @param username a String containing the username of the user obtained from the token
     * @return Player object containing user's player stats
     */
    private Player findPlayerFromUsername(String username) {
        //Obtains playerTag from username
        String playerTag = getPlayerTagFromUser(username);

        //Sets headers values of request and constructs a HttpEntity
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //Constructs the URI for the API call to obtain playerInfo
        String url = brawlStarsInstanceUrl + playerInfoPath + playerTag;
        URI uri = URI.create(url);

        //Make a GET call to the API
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                String.class
        );

        //Converts the String into gson using serialized annotation
        Player player = gson.fromJson(responseEntity.getBody(), Player.class);

        //Returns Player in ResponseEntity
        return player;
    }

    /**
     * @param username a String containing the username of the user obtained from the token
     * @return BsPlayerResponse containing the Player object in the body
     */
    @Override
    public Response getBsStats(String username) {
        //Finds the Brawl Stars player from username
        Player player = findPlayerFromUsername(username);

        //Return a BsPlayerResponse
        return BsPlayerResponse.builder()
                .player(player)
                .build();
    }

    /**
     * @param username a String containing the username of the user obtained from the token
     * @return SuccessResponse "User's Brawl Star Stats has been updated successfully"
     */
    @Override
    public Response updateBsStats(String username) {
        //Finds the Brawl Stars player from username
        Player player = findPlayerFromUsername(username);

        //Obtain user from userRepository and throws UsernameNotFoundException if user not found
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Arrange all the brawlers in order of rank, trophies, highest trophies
        Collections.sort(player.getBrawlers());

        //Obtains user from BsProfile
        user.getBsProfile().setPlayer(player);

        //Saves user back into userRepository
        userRepository.save(user);

        //Returns SuccessResponse once the Stats has been updated
        return SuccessResponse.builder()
                .message("User's Brawl Star Stats has been updated successfully")
                .build();
    }

    /**
     * Private Helper: Perform API call to obtain Brawl Star Player Battle Log
     *
     * @param username a String containing the username of the user obtained from the token
     * @return BattleLogList object containing user's battleLog
     */
    private BattleLogList findBattleLogListFromUsername(String username) {
        //Obtains playerTag from username
        String playerTag = getPlayerTagFromUser(username);

        //Sets headers values of request and constructs a HttpEntity
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //Constructs the URI for the API call to obtain playerInfo
        String url = brawlStarsInstanceUrl + playerInfoPath + playerTag + battleLogPath;
        URI uri = URI.create(url);

        //Make a GET call to the API
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                String.class
        );

        //Converts the String into gson using serialized annotation
        BattleLogList battleLogList = gson.fromJson(responseEntity.getBody(), BattleLogList.class);

        //Returns BattleLog in ResponseEntity
        return battleLogList;
    }

    /**
     * @param username a String containing the username of the user obtained from the token
     * @return BsPlayerBattleLogResponse containing the BattleLogList object in the body
     */
    @Override
    public Response getBsBattleLog(String username) {
        //Finds the Brawl Stars battleLog from username
        BattleLogList battleLogList = findBattleLogListFromUsername(username);

        //Return a BsPlayerResponse
        return BsPlayerBattleLogResponse.builder()
                .battleLogList(battleLogList)
                .build();
    }

    /**
     * @param username a String containing the username of the user obtained from the token
     * @return SuccessResponse "User's Brawl Star Battle Log has been updated successfully"
     */
    @Override
    public Response updateBsBattleLog(String username) {
        //Finds the Brawl Stars battleLog from username
        BattleLogList battleLogList = findBattleLogListFromUsername(username);

        //Obtain user from userRepository and throws UsernameNotFoundException if user not found
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Finds wins,losses and draws from past 25 battles
        int wins = 0;
        int losses = 0;
        int draws = 0;

        //Loops through all 25 battleLogs
        for (BattleLog battleLog : battleLogList.getItems()) {
            Battle battle = battleLog.getBattle();
            if (battle.getResult().equals("victory")) {
                wins++;
            } else if (battle.getResult().equals("defeat")) {
                losses++;
            } else if (battle.getResult().equals("draw")) {
                draws++;
            }
        }

        //Updates the variables in the field
        user.setWins(wins);
        user.setLosses(losses);
        user.setDraws(draws);

        //Saves user back into userRepository
        userRepository.save(user);

        //Returns SuccessResponse once the Stats has been updated
        return SuccessResponse.builder()
                .message("User's Brawl Star Battle Log has been updated successfully")
                .build();
    }
}
