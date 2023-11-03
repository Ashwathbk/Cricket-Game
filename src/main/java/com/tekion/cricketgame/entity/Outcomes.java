package com.tekion.cricketgame.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.function.LongFunction;

@Data
@Entity
public class Outcomes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long overId;
    private Long run;
    private Long ballNo;
}
