package com.tekion.cricketgame.controller;

import com.tekion.cricketgame.entity.Match;
import com.tekion.cricketgame.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/apis")
public class MatchController {
    @Autowired
    private MatchService matchService;
    @PostMapping("/printScoreBoard")
    public Map<String, Object> insertScoreBoardDetails(@RequestBody Match matchRequest) {
        return matchService.insertScoreBoardDetails(matchRequest);
    }
}