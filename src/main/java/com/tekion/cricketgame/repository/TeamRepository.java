package com.tekion.cricketgame.repository;

import com.tekion.cricketgame.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
