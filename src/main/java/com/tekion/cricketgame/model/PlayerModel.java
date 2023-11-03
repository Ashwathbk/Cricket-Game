package com.tekion.cricketgame.model;

import lombok.Data;

@Data
public class PlayerModel {
    private Long id;
    private String playerType;
    private Long teamId;
    private String name;
}