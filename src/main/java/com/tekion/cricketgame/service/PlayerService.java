package com.tekion.cricketgame.service;

import com.tekion.cricketgame.entity.PlayerDetails;
import com.tekion.cricketgame.entity.Players;
import com.tekion.cricketgame.model.PlayerModel;

public interface PlayerService{
    Players createPlayerDetails(PlayerModel playerDetails);

    Players getPlayerDetails(Long playerDetailsId);

    Players getPlayerDetailsByMatchId(Long matchId);
}