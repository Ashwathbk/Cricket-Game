package com.tekion.cricketgame.service;

import com.tekion.cricketgame.entity.PlayerDetails;
import com.tekion.cricketgame.entity.Players;
import com.tekion.cricketgame.model.PlayerModel;

import java.util.List;

public interface PlayerService{
    Players createPlayer(PlayerModel playerModel);

    Players getPlayersById(Long playerDetailsId);

    Players getPlayerDetailsByMatchId(Long matchId);

    List<Players> getAllPlayers();

    Players updatePlayers(Players players);

    void deleteTeam(Long userId);
}