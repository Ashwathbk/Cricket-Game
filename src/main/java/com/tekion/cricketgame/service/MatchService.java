package com.tekion.cricketgame.service;

import com.tekion.cricketgame.entity.InningsDetails;
import com.tekion.cricketgame.entity.Match;
import com.tekion.cricketgame.model.MatchEntry;
import com.tekion.cricketgame.model.MatchResponse;

import java.util.Map;

public interface MatchService {
    Map<String, Object> insertScoreBoardDetails(Match matchRequest);

    InningsDetails getMatchDetailsById(Long matchId);

    MatchResponse playTheMatch(MatchEntry match);
}
