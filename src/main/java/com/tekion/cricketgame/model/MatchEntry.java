package com.tekion.cricketgame.model;

import com.tekion.cricketgame.entity.Team;
import lombok.Data;

@Data
public class MatchEntry {
    private Long id;
    private Long team1;
    private Long team2;
    private String matchType;
    private String venue;
}
