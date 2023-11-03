package com.tekion.cricketgame.service.impl;

import com.tekion.cricketgame.entity.ScoreBoard;
import com.tekion.cricketgame.repository.ScoreboardRepository;
import com.tekion.cricketgame.service.ScoreBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScoreBoardServiceImpl implements ScoreBoardService {
   @Autowired
    ScoreboardRepository scoreboardRepository;

    @Override
    public ScoreBoard getScoreBoardDataById(Long scoreBoardId) {
        Optional<ScoreBoard> scoreBoard = scoreboardRepository.findById(scoreBoardId);
        return scoreBoard.get();
    }
}