package com.tekion.cricketgame.service;

import com.tekion.cricketgame.entity.Match;

import java.util.Map;

public interface MatchService {
    Map<String, Object> insertScoreBoardDetails(Match matchRequest);
}
