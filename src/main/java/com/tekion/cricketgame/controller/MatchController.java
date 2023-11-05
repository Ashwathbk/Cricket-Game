package com.tekion.cricketgame.controller;

import com.tekion.cricketgame.entity.InningsDetails;
import com.tekion.cricketgame.entity.Match;

import com.tekion.cricketgame.model.*;
import com.tekion.cricketgame.entity.ScoreBoard;
import com.tekion.cricketgame.model.MatchEntry;
import com.tekion.cricketgame.model.MatchResponse;
import com.tekion.cricketgame.service.MatchService;
import com.tekion.cricketgame.service.PlayerDetailsService;
import com.tekion.cricketgame.service.ScoreBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/apis")
public class MatchController {
    @Autowired
    private ScoreBoardService scoreBoardService;
    @Autowired
    private MatchService matchService;
    @PostMapping("/printScoreBoard")
    public Map<String, Object> insertScoreBoardDetails(@RequestBody Match matchRequest) {
        return matchService.insertScoreBoardDetails(matchRequest);
    }
    @PostMapping("/getScoreBoard/{id}")
    public ResponseEntity<ScoreBoard> getScoreBoardDataById(@PathVariable("id") Long scoreBoardId){
        ScoreBoard scoreBoard = scoreBoardService.getScoreBoardDataById(scoreBoardId);
        return new ResponseEntity<>(scoreBoard, HttpStatus.OK);
    }
    @PostMapping("/getMatchDetails/{matchId}")
    public ResponseEntity<InningsDetails> getMatchDetailsById(@PathVariable("matchId") Long matchId){
        InningsDetails inningsDetails = matchService.getMatchDetailsById(matchId);
        return new ResponseEntity<>(inningsDetails, HttpStatus.OK);
    }
    @PostMapping("/playTheMatch")
    public ResponseEntity<MatchResponse> playTheMatch(@RequestBody MatchEntry match){
        MatchResponse matchResponse = matchService.playTheMatch(match);
        return new ResponseEntity<>(matchResponse, HttpStatus.OK);
    }

    @GetMapping("/getMatchDetailsById/{id}")
    public ResponseEntity<ScoreBoardResponseModel> getPayerListByMatchId(@PathVariable("id") Long matchId){
        ScoreBoardResponseModel response = matchService.getScoreBoardByMatchId(matchId);
        return ResponseEntity.ok().body(response);
    }

//    @GetMapping(CricketApiConstants.GET_PLAYER_DETAILS_BY_MATCH_ID)
//    public ResponseEntity<PlayerDetailByMatchResponseModel> getPlayerDetailsByMatchId(@RequestBody MatchMapperModel matchId){
//
//        PlayerDetailByMatchResponseModel response = matchService.getPlayerDetailsByMatchId(matchId.getId());
//        return ResponseEntity.ok().body(response);
//    }


}