package com.tekion.cricketgame.service.impl;

import com.tekion.cricketgame.entity.PlayerDetails;
import com.tekion.cricketgame.entity.Players;
import com.tekion.cricketgame.entity.Team;
import com.tekion.cricketgame.model.PlayerModel;
import com.tekion.cricketgame.repository.PlayersRepository;
import com.tekion.cricketgame.repository.TeamRepository;
import com.tekion.cricketgame.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    PlayersRepository playersRepository;

    @Autowired
    TeamRepository teamRepository;
    @Override
    public Players createPlayerDetails(PlayerModel playerDetails) {

        Optional<Team> id = teamRepository.findById(playerDetails.getTeamId());

        Players players = new Players();
        players.setPlayerType(playerDetails.getPlayerType());
        int intOne = 1;
      //  Long longOne = (long) intOne;
        Team team = new Team();
        team.setId(id.get().getId());
        team.setName(id.get().getName());
        team.setCountry(id.get().getCountry());
        //team.setPlayers(id.get().getPlayers());
        players.setTeam(team);
        players.setRunsScored(0);
        players.setWicketTaken(0);
        players.setName(playerDetails.getName());

        return playersRepository.save(players);
    }

    @Override
    public Players getPlayerDetails(Long playerDetailsId) {
        Optional<Players> optionalPDetails = playersRepository.findById(playerDetailsId);
        return optionalPDetails.get();
    }

    @Override
    public Players getPlayerDetailsByMatchId(Long matchId) {
        Optional<Players> playerDetailsByMatchId = playersRepository.findById(matchId);
        return playerDetailsByMatchId.get();
    }
}