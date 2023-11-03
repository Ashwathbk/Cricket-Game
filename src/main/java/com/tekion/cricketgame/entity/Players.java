package com.tekion.cricketgame.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
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

        @Column(nullable = false)
        private int runsScored;

        @Column(nullable = false)
        private int wicketTaken;

        @ManyToOne // Assuming this establishes the relationship with Team
        @JoinColumn(name = "team_id") //foreign key column name
        private Team team;

        public Players(String name) {
            this.name = name;
            this.runsScored = 0;
        }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public Players(){
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

}