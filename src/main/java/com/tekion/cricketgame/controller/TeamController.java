package com.tekion.cricketgame.controller;

import com.tekion.cricketgame.entity.Team;
import com.tekion.cricketgame.model.TeamModel;
import com.tekion.cricketgame.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis")
public class TeamController {
    @Autowired
    TeamService teamService;

    @PostMapping("/createTeam")
    public ResponseEntity<TeamModel> createTeam(@RequestBody TeamModel team) {
        TeamModel teamResponse = teamService.createTeam(team);
        return new ResponseEntity<>(teamResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getTeamById/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable("id") Long teamId) {
        Team teamModel = teamService.getTeamById(teamId);
        return new ResponseEntity<>(teamModel, HttpStatus.OK);//N
    }

    @GetMapping("/getAllTeams")
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teamsList = teamService.getAllTeams();
        return new ResponseEntity<>(teamsList, HttpStatus.OK);
    }

    @PutMapping("/updateTeams/{id}")
    public ResponseEntity<TeamModel> updateUser(@PathVariable("id") Long userId,
                                                @RequestBody TeamModel teamModel) {
        teamModel.setId(userId);
        TeamModel updatedUser = teamService.updateTeam(teamModel);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
         teamService.deleteTeam(userId);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }
}