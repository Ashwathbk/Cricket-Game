package com.tekion.cricketgame.repository;

import com.tekion.cricketgame.entity.Players;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayersRepository extends JpaRepository<Players, Long> {
    List<Players> findByTeamId(long id);
}
