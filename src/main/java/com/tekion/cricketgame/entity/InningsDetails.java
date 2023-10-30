package com.tekion.cricketgame.entity;

import jakarta.persistence.criteria.CriteriaBuilder;

public class InningsDetails {
   private Long innings_id;
    private Integer match_id;
    private Integer team_id;
    private Integer innings_number;
    private Integer total_runs;
    private Integer total_wickets_lost;
    private Integer total_overs;
    private Integer total_extras;
}
