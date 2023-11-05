package com.tekion.cricketgame.service.impl;

import com.tekion.cricketgame.entity.*;
import com.tekion.cricketgame.model.MatchEntry;
import com.tekion.cricketgame.model.MatchResponse;
import com.tekion.cricketgame.model.ScoreBoardResponseModel;
import com.tekion.cricketgame.repository.*;
import com.tekion.cricketgame.service.MatchService;
import com.tekion.cricketgame.util.ScoreGenerator;
import org.hibernate.sql.ast.tree.expression.Over;
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
    MatchRepository matchRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    OverDetailsRepository overDetailsRepository;

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
            firstInningsDetails.setMatchId((long) Math.toIntExact(matchRequest.getId()));
            firstInningsDetails.setInningsNumber(1);
            LOGGER.info("First innings details : " + firstInningsDetails);

            //InningsDetails secondInningsDetails = playInnings(team2, team1);
            Map<String, Object> res1 = matchResult(team2, team1, matchType, matchId);
            InningsDetails secondInningsDetails = (InningsDetails) res1.get("inningsDetails");
            secondInningsDetails.setMatchId((long) Math.toIntExact(matchRequest.getId()));
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
            battingDetailsOfTeamA.put("scoreBoard", scoreBoard);

            ScoreBoard scoreBoard1 = scoreboardRepository.save(scoreBoard);
            InningsDetails inningsDetailsFirst = inningsDetailsRepository.save(firstInningsDetails);
            InningsDetails inningsDetailsSecond = inningsDetailsRepository.save(secondInningsDetails);

        } catch (Exception e) {
            LOGGER.error("Exception something went wrong " + e);
        }
        return battingDetailsOfTeamA;
    }

    public static Map<String, Object> matchResult(Team teamA, Team teamB, String matchType, Long matchId) {
        Map<String, Object> response = new HashMap<>();
        int overs = 0;
        if (matchType.equals("ODI")) {
            overs = 50;
        } else if (matchType.equals("T20")) {
            overs = 20;
        }
        Map<String, Object> innings = playInnings(teamA, teamB, overs, matchId);
        response.put("inningsDetails", innings.get("inningsDetails"));
        response.put("overDetails", innings.get("overDetails"));
        return response;
    }

    public static Map<String, Object> playInnings(Team teamA, Team teamB, int oversForAMatch, Long matchId) {
        InningsDetails inningsDetails = new InningsDetails();
        List<Object> result = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        int count = 0;
        int event = 0;
        int totalScores = 0;
        int runs = 0;
        int ballsPerOver = 0;
        int oversPerRun = 0;
        int ballNo = 1; // Initialize the ball number
        Long overId = 1L; // Initialize the over ID
        List<Outcomes> ballDetailsList = new ArrayList<>();

        Long totalBalls = 0L;
        Long totalRuns = 0L;
        List<Integer> ballDetails = new ArrayList<>();

        for (int over = 1; over <= oversForAMatch; over++) {
            for (int ball = 1; ball <= 6; ball++) {
                totalBalls++; // Increment the total balls bowled
                ballsPerOver++; // Increment the balls in the current over
                for (Players batsman : teamA.getPlayers()) {
                    // Create a BallDetails object for the current ball
                    Outcomes ballDetail = new Outcomes();
                    String eve = ScoreGenerator.getRandomScore();
                    if (count < 10) {
                        if (eve.equals("W")) {
                            batsman.addRuns(runs);
                            ballDetails.add(-1);
                            runs = 0;
                            count++;
                        } else {
                            event = Integer.parseInt(eve);
                            if (count <= 10) {
                                totalScores = totalScores + event;
                                runs = runs + event;
                                ballDetails.add(event); // Add the run scored to ballDetails
                                // overDetails.addBallDetail(event);
                            }
                        }
                        ballDetail.setOverId(overId);
                        ballDetail.setBallNo((long) ballNo);
                        ballDetail.setRun((long) event);
                        ballDetailsList.add(ballDetail);
                        System.out.println("Total ballDetail: " + ballDetail);
                        // Increment the ball number
                        ballNo++;
                    } else {
                        break;
                    }
                }
            }
            if (ballsPerOver == 6) {
                oversPerRun++;
                ballsPerOver = 0;
            }
            // Increment the over ID
            overId++;
        }

        System.out.println("Total ballDetailsList: " + ballDetailsList);
        System.out.println("Total Runs: " + totalScores);
        System.out.println("Total Balls: " + totalBalls);
        System.out.println("Overs Played: " + oversPerRun + "." + ballsPerOver);
        System.out.println("Ball Details: " + ballDetails);

        OverDetails overDetails = new OverDetails();
        overDetails.setTotalRuns((long) totalScores);
        overDetails.setTotalBall(totalBalls);
        overDetails.setInningsId(matchId);

        LOGGER.info("Total Scores : " + totalScores);
        response.put("battingScores", result);
        inningsDetails.setTotalRuns(totalScores);
        inningsDetails.setTeamId((long) Math.toIntExact(teamA.getId()));
        inningsDetails.setTotalExtras(0);

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
        response.put("overDetails", overDetails);
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
            Optional<Team> teamA = teamRepository.findById(team1);
            Optional<Team> teamB = teamRepository.findById(team2);

            Map<String, Object> res = matchResult(teamA.get(), teamB.get(), matchType, 1L);
            Map<String, Object> res1 = matchResult(teamB.get(), teamA.get(), matchType, 2L);
            InningsDetails firstInningsDetails = (InningsDetails) res.get("inningsDetails");
            InningsDetails secondInningsDetails = (InningsDetails) res1.get("inningsDetails");
            firstInningsDetails.setInningsNumber(1);
            secondInningsDetails.setInningsNumber(2);
            firstInningsDetails.setMatchId(matchId);
            secondInningsDetails.setMatchId(matchId);

            LOGGER.info("First innings details : " + firstInningsDetails);
            LOGGER.info("Second innings details : " + secondInningsDetails);

            int teamAResult = firstInningsDetails.getTotalRuns();
            int teamBResult = secondInningsDetails.getTotalRuns();
            // difference = teamAResult - teamBResult;

            String result = Optional.of(teamAResult - teamBResult)
                    .filter(differences -> differences > 0)
                    .map(differences -> "Team " + teamA.get().getName() + " Won the match by : " + differences + " runs")
                    .orElseGet(() -> teamAResult == teamBResult ? "Match Drawn!" : "Team " + teamB.get().getName() + " Won the match by : " + Math.abs(teamAResult - teamBResult) + " runs");

            LOGGER.info("Result of the match : " + result);

            matchResponse.setVenue(matchRequest.getVenue());//A
            matchResponse.setTeam1Id(teamA.get().getId());
            matchResponse.setTeam2Id(teamB.get().getId());
            matchResponse.setTeamAScore((long) teamAResult);
            matchResponse.setTeamBScore((long) teamBResult);
            matchResponse.setTeam1Name(teamA.get().getName());
            matchResponse.setTeam2Name(teamB.get().getName());
            matchResponse.setMatchResult(result);

            Match match = new Match();
            match.setTeam1(teamA.get());
            match.setTeam2(teamB.get());
            match.setManOfTheMatch("NA");
            match.setMatchType(matchType);
            match.setMatchResult(result);
            match.setVenue(matchRequest.getVenue());

            Match matchResp = matchRepository.save(match);
            LOGGER.info("Match ~~~~~~~~~~" + matchResp);

            // Insertion for Score board.
            ScoreBoard scoreBoard = new ScoreBoard();
            scoreBoard.setTotalWickets(firstInningsDetails.getTotalWicketsLost());
            scoreBoard.setResult(result);
            scoreBoard.setOversBowled(firstInningsDetails.getTotalOvers());
            scoreBoard.setTotalRuns(firstInningsDetails.getTotalRuns());

            //Insertion of Over details
            OverDetails firstOverDetails = (OverDetails) res.get("overDetails");
            OverDetails secondOverDetails = (OverDetails) res1.get("overDetails");
            saveOverDetails(firstOverDetails, secondOverDetails);

            //Insert PlayerDetails into DB
           // savePlayerDetails(players);

            ScoreBoard scoreBoard1 = scoreboardRepository.save(scoreBoard);
            InningsDetails inningsDetailsFirst = inningsDetailsRepository.save(firstInningsDetails);
            InningsDetails inningsDetailsSecond = inningsDetailsRepository.save(secondInningsDetails);

        } catch (Exception e) {
            LOGGER.error("Exception something went wrong " + e);
        }
        return matchResponse;
    }

    public static MatchEntry mapMatchEntityToMatchModel(Match matchEntity) {
        MatchEntry matchEntryModel = new MatchEntry();
        matchEntryModel.setId(matchEntity.getId());
        matchEntryModel.setMatchType(matchEntity.getMatchType());
        matchEntryModel.setTeam1(matchEntity.getTeam1().getId());
        matchEntryModel.setTeam2(matchEntity.getTeam2().getId());
        return matchEntryModel;
    }

    public void saveOverDetails(OverDetails firstOverDetails, OverDetails secondOverDetails) {
        OverDetails overDetails = new OverDetails();
        overDetails.setInningsId(firstOverDetails.getInningsId());
        overDetails.setTotalRuns(firstOverDetails.getTotalRuns());
        overDetails.setTotalBall(firstOverDetails.getTotalBall());
        overDetails.setBallDetails(firstOverDetails.getBallDetails());
        OverDetails saveFirstOverDetails = overDetailsRepository.save(overDetails);

        OverDetails overDetails1 = new OverDetails();
        overDetails1.setInningsId(secondOverDetails.getInningsId());
        overDetails1.setTotalRuns(secondOverDetails.getTotalRuns());
        overDetails1.setTotalBall(secondOverDetails.getTotalBall());
        overDetails1.setBallDetails(secondOverDetails.getBallDetails());
        OverDetails saveSecondOverDetails = overDetailsRepository.save(overDetails1);
        LOGGER.info("First OverDetails saved status : " + saveFirstOverDetails);
        LOGGER.info("Second OverDetails saved status : " + saveSecondOverDetails);
    }


    @Override
    public ScoreBoardResponseModel getScoreBoardByMatchId(Long matchId) {
        ScoreBoardResponseModel responseModel=new ScoreBoardResponseModel();

        Optional<Match> matchDetails = matchRepository.findById(matchId);
        LOGGER.info("!!!" + matchDetails);
        List<Players> teamAList = playersRepository.findByTeamId(matchDetails.get().getTeam1().getId());
        LOGGER.info("!!!" + teamAList);

        Map<String, Object> teamAResponse = new HashMap<>();
        teamAResponse.put("teamADetails", matchDetails);
        teamAResponse.put("teamAPlayerDetails", teamAList);

        Optional<Match> matchDetails1 = matchRepository.findById(matchId);
        LOGGER.info("!!!" + matchDetails1);
        List<Players> teamAList1 = playersRepository.findByTeamId(matchDetails1.get().getTeam2().getId());
        LOGGER.info("!!!" + teamAList1);

        Map<String, Object> teamBResponse = new HashMap<>();
        teamBResponse.put("teamBDetails", matchDetails);
        teamBResponse.put("teamBPlayerDetails", teamAList);
        responseModel.setTeamA(teamAResponse);
        responseModel.setTeamB(teamBResponse);

        return responseModel;
    }
}