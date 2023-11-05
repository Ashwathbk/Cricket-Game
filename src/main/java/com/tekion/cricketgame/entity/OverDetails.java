package com.tekion.cricketgame.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.util.*;

@Data
@Entity
public class OverDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long inningsId;
    private Long totalBall;
    private Long totalRuns;
    private List<Integer> ballDetails = new ArrayList<>();

    public void addBallDetail(int runs) {
        ballDetails.add(runs);
    }
}
