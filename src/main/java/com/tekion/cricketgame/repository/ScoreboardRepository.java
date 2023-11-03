package com.tekion.cricketgame.repository;

import com.tekion.cricketgame.entity.ScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreboardRepository extends JpaRepository<ScoreBoard, Long> {
}
