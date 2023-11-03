package com.tekion.cricketgame.service.impl;

import com.tekion.cricketgame.entity.*;
import com.tekion.cricketgame.model.MatchEntry;
import com.tekion.cricketgame.model.MatchResponse;
import com.tekion.cricketgame.repository.InningsDetailsRepository;
import com.tekion.cricketgame.repository.PlayersRepository;
import com.tekion.cricketgame.repository.ScoreboardRepository;
import com.tekion.cricketgame.repository.TeamRepository;
import com.tekion.cricketgame.service.MatchService;
import com.tekion.cricketgame.util.ScoreGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MatchServiceImpl implements MatchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchServiceImpl.class);
    @Autowired
    ScoreboardRepository scoreboardRepository;
    @Autowired
    InningsDetailsRepository inningsDetailsRepository;
    @Autowired
    PlayersRepository playersRepository;

    @Autowired
    TeamRepository teamRepository;

    @Override
    public Map<String, Object> insertScoreBoardDetails(Match matchRequest) {
        Map<String, Object> battingDetailsOfTeamA = new HashMap<>();
        try {
            String matchType = matchRequest.getMatchType();
            Long matchId = matchRequest.getId();
        Team team1 = matchRequest.getTeam1();
        Team team2 = matchRequest.getTeam2();
        /* Code for playing the first innings using team1 and return the innings details. */
        Map<String, Object> res = matchResult(team1, team2, matchType, matchId);
        InningsDetails firstInningsDetails = (InningsDetails) res.get("inningsDetails");

        //InningsDetails firstInningsDetails = playInnings(team1, team2);
        firstInningsDetails.setMatchId(Math.toIntExact(matchRequest.getId()));
        firstInningsDetails.setInningsNumber(1);
        LOGGER.info("First innings details : " + firstInningsDetails);

        //InningsDetails secondInningsDetails = playInnings(team2, team1);
        Map<String, Object> res1 = matchResult(team2, team1, matchType, matchId);
        InningsDetails secondInningsDetails = (InningsDetails) res1.get("inningsDetails");
        secondInningsDetails.setMatchId(Math.toIntExact(matchRequest.getId()));
        secondInningsDetails.setInningsNumber(2);
        LOGGER.info("Second innings details : " + secondInningsDetails);

        int difference = 0;
        int teamAResult = firstInningsDetails.getTotalRuns();
        int teamBResult = secondInningsDetails.getTotalRuns();
        // difference = teamAResult - teamBResult;

        String result = Optional.of(teamAResult - teamBResult)
                .filter(differences -> differences > 0)
                .map(differences -> "Team " + team1.getName() + " Won the match by : " + differences + " runs")
                .orElseGet(() -> teamAResult == teamBResult ? "Match Drawn!" : "Team " + team2.getName() + " Won the match by : " + Math.abs(teamAResult - teamBResult) + " runs");

        LOGGER.info("Result of the match : " + result);

        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.setTotalWickets(firstInningsDetails.getTotalWicketsLost());
        scoreBoard.setResult(result);
        scoreBoard.setOversBowled(firstInningsDetails.getTotalOvers());
        scoreBoard.setTotalRuns(firstInningsDetails.getTotalRuns());

        matchRequest.setMatchResult(result); // matchType, venue, MOM, result

        battingDetailsOfTeamA.put("match", matchRequest);
        battingDetailsOfTeamA.put("scoreBoard",scoreBoard);

        ScoreBoard scoreBoard1 =   scoreboardRepository.save(scoreBoard);
        InningsDetails inningsDetailsFirst = inningsDetailsRepository.save(firstInningsDetails);
        InningsDetails inningsDetailsSecond = inningsDetailsRepository.save(secondInningsDetails);

       for(Players players : team1.getPlayers()){
            playersRepository.save(players);
        }
        for(Players players2 : team2.getPlayers()){
            playersRepository.save(players2);
        }

        }catch (Exception e){
            LOGGER.error("Exception something went wrong " + e);
        }
        return battingDetailsOfTeamA;
    }
    public static Map<String, Object> matchResult(Team teamA, Team teamB, String matchType, Long matchId){
        Map<String, Object> response = new HashMap<>();
        int overs = 0;
        if(matchType.equals("ODI")){
            overs = 50;
        } else if (matchType.equals("T20")) {
            overs = 20;
        }
        Map<String, Object> innings = playInnings(teamA, teamB, overs, matchId);
        response.put("inningsDetails", innings.get("inningsDetails"));
        return response;
    }

    public static Map<String, Object> playInnings(Team teamA, Team teamB, int oversForAMatch, Long matchId){
        InningsDetails inningsDetails = new InningsDetails();
        List<Object> result = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        //Players batsman = new Players();
        //OverDetails overDetails = new OverDetails();

        int count = 0;
        int event = 0;
        int totalScores = 0;
        int runs = 0;
        for (int over = 1; over <= oversForAMatch; over++) {
            OverDetails overDetails = new OverDetails();
            for (int ball = 1; ball <= 6; ball++) {
                for (Players batsman : teamA.getPlayers()) {
                    String eve = ScoreGenerator.getRandomScore();
                    if (eve.equals("W")) {
                        batsman.addRuns(runs);
                        //batsman.setMatchId(matchId);
                        //batsman.setRunsScored(runs);
                        /* result.add(batsman); */
                        runs = 0;
                        count++;
                    } else {
                        event = Integer.parseInt(eve);
                        if (count <= 10) {
                            totalScores = totalScores + event;
                            runs = runs + event;
                            overDetails.addBallDetail(event);
                        }
                    }
                }
            }
           // overDetails.setInningsId(inningsDetails.getInningsId());
            //overDetails.setTotalRuns();
        }
        LOGGER.info("Total Scores : "+ totalScores);
        response.put("battingScores", result);
        inningsDetails.setTotalRuns(totalScores);
        inningsDetails.setTeamId(Math.toIntExact(teamA.getId()));

        /* Generating the bowling details from here onwards. **/
        List<String> bowlerData = new ArrayList<>();
        int totalWickets = 0;
        int totalOvers = 0;
        int maxWicketsAllowed = 10;
        int bowlerCount = 5;
        int maxWicketsPerBowler = maxWicketsAllowed / bowlerCount;
        int overBowledCount = 0;
        Random random = new Random();

        for (int i = 5; i < teamB.getPlayers().size(); i++) {
            Players bowler = teamB.getPlayers().get(i);
            /** for (int bowler = 1; bowler <= bowlerCount; bowler++) { */
            int wicketsTaken = 0;
            int oversBowled = 0;

            int oversPerBowler = 0;
            oversPerBowler = (oversForAMatch > 20) ? 10 : 4;

            while (wicketsTaken < maxWicketsPerBowler && totalWickets < maxWicketsAllowed && totalOvers < oversForAMatch && oversBowled < oversPerBowler) {
                int wicketProbability = random.nextInt(10);
                if (wicketProbability < 2) {
                    wicketsTaken++;
                    totalWickets++;
                }
                oversBowled++;
                totalOvers++;
            }
            LOGGER.info("Bowler " + bowler.getName() + " took " + wicketsTaken + " wickets in " + oversBowled + " overs.");
           // String str = "Bowler " + bowler.getName() + " took " + wicketsTaken + " wickets in " + oversBowled + " overs.";
            overBowledCount += oversBowled;
            bowler.setWicketTaken(wicketsTaken);
            //bowler.setMatchId(matchId);
           /* bowlerData.add(str); */
        }
        inningsDetails.setTotalOvers(overBowledCount);
        inningsDetails.setTotalWicketsLost(totalWickets);
        LOGGER.info("\nTotal wickets taken by all bowlers: " + totalWickets);
        LOGGER.info("Total overs bowled: " + totalOvers);
        response.put("inningsDetails", inningsDetails);
        return response;
    }
    @Override
    public InningsDetails getMatchDetailsById(Long matchId) {
        Optional<InningsDetails> optionalPDetails = inningsDetailsRepository.findById(matchId);
        return optionalPDetails.get();
    }

    @Override
    public MatchResponse playTheMatch(MatchEntry matchRequest) {
        Map<String, Object> battingDetailsOfTeamA = new HashMap<>();
        MatchResponse matchResponse = new MatchResponse();

        try {
            String matchType = matchRequest.getMatchType();
            Long matchId = matchRequest.getId();
            Long team1 = matchRequest.getTeam1();
            Long team2 = matchRequest.getTeam2();

//            Team teamA = new Team();
//            teamA.setId(team1);
//
//            Team teamB = new Team();
//            teamB.setId(team2);

            Optional<Team> teamA = teamRepository.findById(team1);
            Optional<Team> teamB = teamRepository.findById(team2);

            /* Code for playing the first innings using team1 and return the innings details. */
            Map<String, Object> res = matchResult(teamA.get(), teamB.get(), matchType, matchId);
            InningsDetails firstInningsDetails = (InningsDetails) res.get("inningsDetails");

            //InningsDetails firstInningsDetails = playInnings(team1, team2);
           // firstInningsDetails.setMatchId(Math.toIntExact(matchRequest.getId()));
            firstInningsDetails.setInningsNumber(1);
            LOGGER.info("First innings details : " + firstInningsDetails);

            //InningsDetails secondInningsDetails = playInnings(team2, team1);
            Map<String, Object> res1 = matchResult(teamB.get(), teamA.get(), matchType, matchId);

            InningsDetails secondInningsDetails = (InningsDetails) res1.get("inningsDetails");
            //secondInningsDetails.setMatchId(Math.toIntExact(matchRequest.getId()));
            secondInningsDetails.setInningsNumber(2);
            LOGGER.info("Second innings details : " + secondInningsDetails);

            int difference = 0;
            int teamAResult = firstInningsDetails.getTotalRuns();
            int teamBResult = secondInningsDetails.getTotalRuns();
            // difference = teamAResult - teamBResult;

            String result = Optional.of(teamAResult - teamBResult)
                    .filter(differences -> differences > 0)
                    .map(differences -> "Team " + teamA.get().getName() + " Won the match by : " + differences + " runs")
                    .orElseGet(() -> teamAResult == teamBResult ? "Match Drawn!" : "Team " + teamB.get().getName() + " Won the match by : " + Math.abs(teamAResult - teamBResult) + " runs");

            LOGGER.info("Result of the match : " + result);

            ScoreBoard scoreBoard = new ScoreBoard();
            scoreBoard.setTotalWickets(firstInningsDetails.getTotalWicketsLost());
            scoreBoard.setResult(result);
            scoreBoard.setOversBowled(firstInningsDetails.getTotalOvers());
            scoreBoard.setTotalRuns(firstInningsDetails.getTotalRuns());

            //matchRequest.setMatchResult(result); // matchType, venue, MOM, result

            battingDetailsOfTeamA.put("match", matchRequest);
            matchResponse.setMatch(matchRequest);
            matchResponse.setFirstInningDetails(firstInningsDetails);
            matchResponse.setSecondInningsDetails(secondInningsDetails);
            matchResponse.setScoreBoard(scoreBoard);

            battingDetailsOfTeamA.put("scoreBoard",scoreBoard);

            ScoreBoard scoreBoard1 =   scoreboardRepository.save(scoreBoard);
            InningsDetails inningsDetailsFirst = inningsDetailsRepository.save(firstInningsDetails);
            InningsDetails inningsDetailsSecond = inningsDetailsRepository.save(secondInningsDetails);

            for(Players players : teamA.get().getPlayers()){
                playersRepository.save(players);
            }
            for(Players players2 : teamB.get().getPlayers()){
                playersRepository.save(players2);
            }
        }catch (Exception e){
            LOGGER.error("Exception something went wrong " + e);
        }
        return matchResponse;
    }

    public static MatchEntry mapMatchEntityToMatchModel(Match matchEntity){
        MatchEntry matchEntryModel = new MatchEntry();
        matchEntryModel.setId(matchEntity.getId());
        matchEntryModel.setMatchType(matchEntity.getMatchType());
        matchEntryModel.setTeam1(matchEntity.getTeam1().getId());
        matchEntryModel.setTeam2(matchEntity.getTeam2().getId());
        return matchEntryModel;
    }
}