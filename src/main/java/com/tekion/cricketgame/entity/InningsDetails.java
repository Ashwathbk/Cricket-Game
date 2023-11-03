package com.tekion.cricketgame.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Entity
@Data
public class InningsDetails {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long inningsId;//        PK
    private Integer matchId;//     FK
    private Integer teamId; //        FK
    private Integer inningsNumber;
    private Integer totalRuns;
    private Integer totalWicketsLost;
    private Integer totalOvers;
    private Integer totalExtras;
}
