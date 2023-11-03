package com.tekion.cricketgame.repository;

import com.tekion.cricketgame.entity.Players;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayersRepository extends JpaRepository<Players, Long> {
}
