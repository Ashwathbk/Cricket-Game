package com.tekion.cricketgame.model;

import lombok.Data;

import java.util.Map;

@Data
public class ScoreBoardResponseModel {

    Map<String, Object> teamA;
    Map<String, Object> teamB;
}
