package com.tekion.cricketgame.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "player_details")
public class PlayerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerDetailsId;

    @Column(nullable = false)
    private Integer teamId; //       FK

    @Column(nullable = false)
    private Integer playerId; //        FK

    @Column(nullable = false)
    private Integer matchId; //		FK

    @Column(nullable = false)
    private Integer runsScored;

    @Column(nullable = false)
    private Integer wicketTaken;

}