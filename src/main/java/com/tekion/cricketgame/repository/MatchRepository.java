package com.tekion.cricketgame.repository;

import com.tekion.cricketgame.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
