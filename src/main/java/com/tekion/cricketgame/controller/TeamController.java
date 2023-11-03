package com.tekion.cricketgame.controller;

import com.tekion.cricketgame.entity.Team;
import com.tekion.cricketgame.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis")
public class TeamController {
    @Autowired
    TeamService teamService;
    @PostMapping("/createTeam")
    public ResponseEntity<Team> createPlayer(@RequestBody Team team){
        Team teamResponse = teamService.createTeam(team);
        return new ResponseEntity<>(teamResponse, HttpStatus.CREATED);
    }
}