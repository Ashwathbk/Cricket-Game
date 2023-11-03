package com.tekion.cricketgame.repository;

import com.tekion.cricketgame.entity.PlayerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerDetailsRepository extends JpaRepository<PlayerDetails, Long> {
}
