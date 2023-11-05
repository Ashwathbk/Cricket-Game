package com.tekion.cricketgame.service.impl;

import com.tekion.cricketgame.entity.PlayerDetails;
import com.tekion.cricketgame.entity.Players;
import com.tekion.cricketgame.entity.Team;
import com.tekion.cricketgame.model.PlayerModel;
import com.tekion.cricketgame.model.TeamModel;
import com.tekion.cricketgame.repository.PlayersRepository;
import com.tekion.cricketgame.repository.TeamRepository;
import com.tekion.cricketgame.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerServiceImpl.class);

    @Autowired
    PlayersRepository playersRepository;

    @Autowired
    TeamRepository teamRepository;
    @Override
    public Players createPlayer(PlayerModel playerDetails) {
        Optional<Team> id = teamRepository.findById(playerDetails.getTeamId());
        Players players = new Players();
        players.setPlayerType(playerDetails.getPlayerType());
        Team team = new Team();
        team.setId(id.get().getId());
        team.setName(id.get().getName());
        team.setCountry(id.get().getCountry());
        team.setPlayers(id.get().getPlayers());
        players.setTeam(team); // For getting team details
//        players.setRunsScored(0);
//        players.setWicketTaken(0);
        players.setName(playerDetails.getName());

        return playersRepository.save(players);
    }

    @Override
    public Players getPlayersById(Long playerDetailsId) {
        Optional<Players> optionalPDetails = playersRepository.findById(playerDetailsId);
        return optionalPDetails.get();
    }

    @Override
    public List<Players> getAllPlayers() {
        return playersRepository.findAll();
    }

    @Override
    public Players updatePlayers(Players players) {
        Players players1 = new Players();

        try {
        players1 = playersRepository.save(players);

        } catch (Exception e) {
            LOGGER.info("Exception occur :" + e);
        }
        return players1;
    }

    @Override
    public void deleteTeam(Long playersId) {
        try {
            teamRepository.deleteById(playersId);
        } catch (Exception e) {
            LOGGER.info("Exception occur :" + e);
        }
    }

    @Override
    public Players getPlayerDetailsByMatchId(Long matchId) {
        Optional<Players> playerDetailsByMatchId = playersRepository.findById(matchId);
        return playerDetailsByMatchId.get();
    }
}