package com.tekion.cricketgame.model;

import com.tekion.cricketgame.entity.InningsDetails;
import com.tekion.cricketgame.entity.ScoreBoard;
import lombok.Data;

@Data
public class MatchResponse {
    private MatchEntry match;
    private InningsDetails firstInningDetails;
    private InningsDetails secondInningsDetails;
    private ScoreBoard scoreBoard;
}