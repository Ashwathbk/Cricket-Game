package com.tekion.cricketgame.model;

import com.tekion.cricketgame.entity.InningsDetails;
import com.tekion.cricketgame.entity.ScoreBoard;
import lombok.Data;

@Data
public class MatchResponse {
//    private MatchEntry match;
//    private InningsDetails firstInningDetails;
//    private InningsDetails secondInningsDetails;
//    private ScoreBoard scoreBoard;

    private Long id;
    private Long team1Id;
    private Long team2Id;
    private String team1Name;
    private String team2Name;
    private String venue;
    private Long teamAScore;
    private Long teamBScore;
    private String matchResult;

}