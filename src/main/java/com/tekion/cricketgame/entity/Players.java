package com.tekion.cricketgame.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@AllArgsConstructor
@ToString
public class Players {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//        @Column(nullable = false)
//        private Long matchId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String playerType;

    @ManyToOne // Assuming this establishes the relationship with Team
    @JoinColumn(name = "team_id") //foreign key column name
    private Team team;

    @Column(nullable = false)
    private int runsScored;

    @Column(nullable = false)
    private int wicketTaken;

    public Players() {
    }

    public Players(String name) {
        this.name = name;
        this.runsScored = 0;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public void addRuns(int runs) {
        runsScored += runs;
    }

    public int getScoreboard() {
        return runsScored;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    @Override
    public String toString() {
        return "{" +
                "id : " + id +
                ", name : '" + name + '\'' +
                ", playerType : '" + playerType + '\'' +
                ", runsScored : '" + runsScored + '\'' +
                ", wicketTaken : '" + wicketTaken + '\'' +
                // Other fields
                '}';
    }

}