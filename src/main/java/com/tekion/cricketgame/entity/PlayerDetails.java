package com.tekion.cricketgame.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "player_details")
public class PlayerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer teamId; //       FK

    @Column(nullable = false)
    private Integer playerId; //        FK

    @Column(nullable = false)
    private Integer matchId; //		FK

    @Column(nullable = false)
    private int runsScored;

    @Column(nullable = false)
    private Integer wicketTaken;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String playerType;

//    @Column(nullable = false)
//    private int runsScored;
//
//    @Column(nullable = false)
//    private int wicketTaken;

//    @ManyToOne // Assuming this establishes the relationship with Team
//    @JoinColumn(name = "team_id") //foreign key column name
//    private Team team1;

    public PlayerDetails(String name) {
        this.name = name;
        this.runsScored = 0;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public PlayerDetails(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRuns(int runs) {
        runsScored += runs;
    }

    public int getScoreboard() {
        return runsScored;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }


//    @Override
//    public String toString() {
//        return "{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", playerType='" + playerType + '\'' +
//                ", runsScored=" + runsScored +
//                ", wicketTaken=" + wicketTaken +
//                ", team=" + team +
//                '}';
//    }

    @Override
    public String toString() {
        return "Players{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", playerType='" + playerType + '\'' +
                ", runsScored=" + runsScored +
                ", wicketTaken=" + wicketTaken +
                // Other fields
                '}';
    }




}