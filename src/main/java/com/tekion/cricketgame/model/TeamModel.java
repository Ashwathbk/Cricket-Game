package com.tekion.cricketgame.model;

import com.tekion.cricketgame.entity.Players;
import lombok.Data;

import java.util.List;

@Data
public class TeamModel {
    private Long id;
    private String name;
    private String country;
    private List<Players> players;

}
