package com.tekion.cricketgame.service;

import com.tekion.cricketgame.entity.PlayerDetails;

public interface PlayerDetailsService {
    PlayerDetails createPlayerDetails(PlayerDetails playerDetails);
    PlayerDetails getPlayerDetails(Long playerDetailsId);
    PlayerDetails getPlayerDetailsByMatchId(Long matchId);
}
