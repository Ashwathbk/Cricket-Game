package com.tekion.cricketgame.service.impl;

import com.tekion.cricketgame.entity.Team;
import com.tekion.cricketgame.model.TeamModel;
import com.tekion.cricketgame.repository.PlayersRepository;
import com.tekion.cricketgame.repository.TeamRepository;
import com.tekion.cricketgame.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamServiceImpl.class);
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayersRepository playersRepository;

    @Override
    public TeamModel createTeam(TeamModel team) {
        Team response;
        Team teamEntity = new Team();
        TeamModel teamModel = new TeamModel();
        try {
            teamEntity.setName(team.getName());
            teamEntity.setCountry(team.getCountry());
            response = teamRepository.save(teamEntity);
            teamModel.setId(response.getId());
            teamModel.setName(response.getName());
            teamModel.setCountry(response.getCountry());
        } catch (Exception e) {
            LOGGER.info("Exception occur :" + e);
        }
        return teamModel;
    }

    @Override
    public Team getTeamById(Long teamId) {
        Optional<Team> optionalTeam = null;
        try {
            optionalTeam = teamRepository.findById(teamId);
            LOGGER.info("optionalTeam" + optionalTeam.get());
        } catch (Exception e) {
            LOGGER.info("Exception occur :" + e);
        }
        return optionalTeam.get();
    }

    @Override
    public List<Team> getAllTeams() {
        List<Team> response = null;
        try {
            response = teamRepository.findAll();
            LOGGER.info("optionalTeam" + response);
        } catch (Exception e) {
            LOGGER.info("Exception occur :" + e);
        }
        return response;
    }

    @Override
    public TeamModel updateTeam(TeamModel teamModel) {
        TeamModel teamModel1 = new TeamModel();
        try {
            Team existingTeam = teamRepository.findById(teamModel.getId()).get();
            existingTeam.setName(teamModel.getName());
            existingTeam.setCountry(teamModel.getCountry());
            Team updatedUser = teamRepository.save(existingTeam);
            teamModel1.setId(updatedUser.getId());
            teamModel1.setName(updatedUser.getName());
            teamModel1.setCountry(updatedUser.getCountry());
        } catch (Exception e) {
            LOGGER.info("Exception occur :" + e);
        }
        return teamModel1;
    }

    @Override
    public void deleteTeam(Long userId) {
        try {
            teamRepository.deleteById(userId);
        } catch (Exception e) {
            LOGGER.info("Exception occur :" + e);
        }
    }
}