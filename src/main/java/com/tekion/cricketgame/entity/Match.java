package com.tekion.cricketgame.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "match_entry")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="team_id1", updatable=false)
    private Team team1;

    @ManyToOne
    @JoinColumn(name="team_id2", updatable=false)
    private Team team2;
    private String matchType;
    private String venue;
    private String matchResult;
    private String manOfTheMatch;

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }
}
