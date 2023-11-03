package com.tekion.cricketgame.service.impl;

import com.tekion.cricketgame.entity.PlayerDetails;
import com.tekion.cricketgame.entity.Players;
import com.tekion.cricketgame.entity.Team;
import com.tekion.cricketgame.repository.PlayersRepository;
import com.tekion.cricketgame.repository.TeamRepository;
import com.tekion.cricketgame.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayersRepository playersRepository;

    @Override
    public Team createTeam(Team team) {
        List<Players> list ;
       // list = playersRepository.findById(1);

        return teamRepository.save(team);
    }
}