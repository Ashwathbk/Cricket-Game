package com.tekion.cricketgame.service.impl;

import com.tekion.cricketgame.entity.PlayerDetails;
import com.tekion.cricketgame.repository.PlayerDetailsRepository;
import com.tekion.cricketgame.service.PlayerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerDetailsServiceImpl implements PlayerDetailsService {

    @Autowired
    private PlayerDetailsRepository playerDetailsRepository;
    @Override
    public PlayerDetails createPlayerDetails(PlayerDetails playerDetails) {
      //  playerDetails.setPlayerDetailsId();
        playerDetails.setPlayerId(1);
        playerDetails.setTeamId(1);
        playerDetails.setRunsScored(12);
        playerDetails.setMatchId(1);
        playerDetails.setWicketTaken(0);
        return playerDetailsRepository.save(playerDetails);
    }

    @Override
    public PlayerDetails getPlayerDetails(Long playerDetailsId) {
        Optional<PlayerDetails> optionalPDetails = playerDetailsRepository.findById(playerDetailsId);
        return optionalPDetails.get();
    }

    @Override
    public PlayerDetails getPlayerDetailsByMatchId(Long matchId) {
        Optional<PlayerDetails> playerDetailsByMatchId = playerDetailsRepository.findById(matchId);
        return playerDetailsByMatchId.get();
    }
}