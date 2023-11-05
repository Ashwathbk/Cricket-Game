package com.tekion.cricketgame.repository;

import com.tekion.cricketgame.entity.InningsDetails;
import com.tekion.cricketgame.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InningsDetailsRepository extends JpaRepository<InningsDetails, Long> {
   // InningsDetails findById(Match matchId);
  // InningsDetails findByMatchIdIdAndBattingTeamId(Long matchId,Long teamId);
}
