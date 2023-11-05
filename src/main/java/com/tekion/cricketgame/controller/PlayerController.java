package com.tekion.cricketgame.controller;

import com.tekion.cricketgame.entity.InningsDetails;
import com.tekion.cricketgame.entity.Match;
import com.tekion.cricketgame.entity.Players;
import com.tekion.cricketgame.entity.Team;
import com.tekion.cricketgame.model.PlayerModel;
import com.tekion.cricketgame.model.TeamModel;
import com.tekion.cricketgame.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/createPlayers")
    public ResponseEntity<Players> createPlayer(@RequestBody PlayerModel playerModel){
        Players savedUser = playerService.createPlayer(playerModel);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    @GetMapping("getPlayersById/{id}")
    public ResponseEntity<Players> getPlayersById(@PathVariable("id") Long playerDetailsId){
        Players user = playerService.getPlayersById(playerDetailsId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getAllPlayers")
    public ResponseEntity<List<Players>> getAllTeams() {
        List<Players> playersList = playerService.getAllPlayers();
        return new ResponseEntity<>(playersList, HttpStatus.OK);
    }

    @PutMapping("/updatePlayerById/{id}")
    public ResponseEntity<Players> updateUser(@PathVariable("id") Long userId,
                                                @RequestBody Players players) {
        players.setId(userId);
        Players updatedPlayers = playerService.updatePlayers(players);
        return new ResponseEntity<>(updatedPlayers, HttpStatus.OK);
    }

    @DeleteMapping("deletePlayerById/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        playerService.deleteTeam(userId);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }




    @GetMapping("/player/{matchId}")
    public ResponseEntity<Players> getPlayerDetailsByMatchId(@PathVariable("matchId") Long matchId){
        Players playerDetails = playerService.getPlayerDetailsByMatchId(matchId);
        return new ResponseEntity<>(playerDetails, HttpStatus.OK);
    }


//    @GetMapping("/fetchMatchDetails/{matchId}")
//    public ResponseEntity<InningsDetails> getPlayerDetailsByMatchId(@PathVariable("matchId") Match matchId){
//        InningsDetails inningsDetails = inningsDetailsService.getInningsDetailsByMatchId(matchId);
//        return new ResponseEntity<>(inningsDetails, HttpStatus.OK);
//    }
}