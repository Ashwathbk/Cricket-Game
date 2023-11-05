package com.tekion.cricketgame.service;

import com.tekion.cricketgame.entity.Team;
import com.tekion.cricketgame.model.TeamModel;

import java.util.List;

public interface TeamService {
    TeamModel createTeam(TeamModel team);

    Team getTeamById(Long teamId);

    List<Team> getAllTeams();

    TeamModel updateTeam(TeamModel teamModel);

    void deleteTeam(Long userId);
}