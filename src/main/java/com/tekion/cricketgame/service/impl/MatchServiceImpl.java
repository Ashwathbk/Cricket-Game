package com.tekion.cricketgame.service.impl;

import com.tekion.cricketgame.entity.Match;
import com.tekion.cricketgame.entity.Team;
import com.tekion.cricketgame.service.MatchService;
import com.tekion.cricketgame.util.ScoreGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MatchServiceImpl implements MatchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchServiceImpl.class);
    @Override
    public Map<String, Object> insertScoreBoardDetails(Match matchRequest) {
        Map<String, Object> battingDetailsOfTeamA = new HashMap<>();
        Team team1 = matchRequest.getTeam1();
        Team team2 = matchRequest.getTeam2();
        try{
            battingDetailsOfTeamA = ScoreGenerator.responseRes(team1, team2);
        if(battingDetailsOfTeamA.isEmpty()){
            LOGGER.info("Team A Batting details : " + battingDetailsOfTeamA);
        }else {
            LOGGER.info("Team A Batting details : " + battingDetailsOfTeamA);
        }
        }catch (Exception e){
            LOGGER.info("Error : ", e);
        }
        return battingDetailsOfTeamA;
    }
}