package com.tekion.cricketgame.controller;

import com.tekion.cricketgame.entity.InningsDetails;
import com.tekion.cricketgame.entity.Match;
import com.tekion.cricketgame.entity.Players;
import com.tekion.cricketgame.model.PlayerModel;
import com.tekion.cricketgame.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apis")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/create")
    public ResponseEntity<Players> createPlayer(@RequestBody PlayerModel playerDetails){
        Players savedUser = playerService.createPlayerDetails(playerDetails);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<Players> getPlayersById(@PathVariable("id") Long playerDetailsId){
        Players user = playerService.getPlayerDetails(playerDetailsId);
        return new ResponseEntity<>(user, HttpStatus.OK);
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