package com.tekion.cricketgame.util;

import com.tekion.cricketgame.entity.PlayerDetails;
import com.tekion.cricketgame.entity.Players;
import com.tekion.cricketgame.entity.Team;
import com.tekion.cricketgame.service.impl.MatchServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ScoreGenerator {
    private static final String[] outcomes = {"0", "1", "2", "3", "4", "5", "6", "W"};
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchServiceImpl.class);
    private static final Random random = new Random();

    public static String getRandomScore() {
        int randomIndex = random.nextInt(outcomes.length);
        return outcomes[randomIndex];
    }
    /* Generating the Bowler wickets */
    public static Map<String, Object> bowlerWicketsDetails(Team team) {
        List<String> bowlerData = new ArrayList<>();
        List<String> bowlerDetails = new ArrayList<>();
        Map<String,Object> wicketDetails = new HashMap<>();
        int totalWickets = 0;
        int totalOvers = 0;
        int maxWicketsAllowed = 10;
        int bowlerCount = 5;
        int maxWicketsPerBowler = maxWicketsAllowed / bowlerCount;
        int overBowledCount = 0;
        Random random = new Random();

        for (int i = 5; i < team.getPlayers().size(); i++) {
            Players bowler = team.getPlayers().get(i);

       /** for (int bowler = 1; bowler <= bowlerCount; bowler++) { */

            int wicketsTaken = 0;
            int oversBowled = 0;

            while (wicketsTaken < maxWicketsPerBowler && totalWickets < maxWicketsAllowed && totalOvers < 50 && oversBowled < 10) {
                int wicketProbability = random.nextInt(10);
                if (wicketProbability < 2) {
                    wicketsTaken++;
                    totalWickets++;
                }
                oversBowled++;
                totalOvers++;
            }
           LOGGER.info("Bowler " + bowler.getName() + " took " + wicketsTaken + " wickets in " + oversBowled + " overs.");
            String str = "Bowler " + bowler.getName() + " took " + wicketsTaken + " wickets in " + oversBowled + " overs.";
            overBowledCount += oversBowled;
            bowlerData.add(str);
        }
        wicketDetails.put("bowler", bowlerData);
        wicketDetails.put("totalOverBowled", overBowledCount);
        wicketDetails.put("totalWickets", totalWickets);

        LOGGER.info("\nTotal wickets taken by all bowlers: " + totalWickets);
        LOGGER.info("Total overs bowled: " + totalOvers);
        return wicketDetails;
    }

    /** Code for generating the Batsman score **/
    public static int teamScore(Team team){
        int count = 0;
        int event = 0;
        int totalScores = 0;
        int runs = 0;

        for (int over = 1; over <= 10; over++) {
            for (int ball = 1; ball <= 6; ball++) {
                // int event = random.nextInt(3);
                for (Players batsman : team.getPlayers()) {
                    String eve = ScoreGenerator.getRandomScore();

                    if (eve.equals("W")) {
                        batsman.addRuns(runs);
                        runs = 0;
                        count++;
                    } else {
                        event = Integer.parseInt(eve);
                        if (count <= 10) {
                            totalScores = totalScores + event;
                            runs = runs + event;
                        }
                    }
                }
            }
        }
        return totalScores;
    }

    public static Map<String, Object> result(Team teamA) {
        List<Object> result = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        int count = 0;
        int event = 0;
        int totalScores = 0;
        int runs = 0;
        for (int over = 1; over <= 10; over++) {
            for (int ball = 1; ball <= 6; ball++) {
                for (Players batsman : teamA.getPlayers()) {
                    String eve = ScoreGenerator.getRandomScore();
                    if (eve.equals("W")) {
                        batsman.addRuns(runs);
                        /* result.add(batsman); */
                        runs = 0;
                        count++;
                    } else {
                        event = Integer.parseInt(eve);
                        if (count <= 10) {
                            totalScores = totalScores + event;
                            runs = runs + event;
                        }
                    }
                }
            }
        }
        LOGGER.info("Total Scores : "+ totalScores);

        for (Players batsman : teamA.getPlayers()) {
            result.add(batsman.getName() +" : "+  batsman.getScoreboard());
        }
        response.put("battingScores", result);
        response.put("totalScores", totalScores);
        return response;
    }

    public static Map<String, Object> responseRes(Team teamA, Team teamB){
        Map<String, Object> responseResultA = new HashMap<>();
        Map<String, Object> responseResultB = new HashMap<>();
        Map<String, Object> teamAScores = new HashMap<>();

        responseResultA.put("BattingStat", result(teamA).get("battingScores"));

        responseResultA.put("teamTotalScores", result(teamA).get("totalScores"));
        responseResultA.put("teamBowlingStat", bowlerWicketsDetails(teamA).get("bowler"));
        responseResultA.put("totalOverBowled", bowlerWicketsDetails(teamA).get("totalOverBowled"));
        responseResultB.put("totalWickets", bowlerWicketsDetails(teamA).get("totalWickets"));
        responseResultA.put("teamId", teamA.getId());
        responseResultA.put("teamName", teamA.getName());
        teamAScores.put("team1",  responseResultA);

        responseResultB.put("BattingStat", result(teamB).get("battingScores"));
        responseResultB.put("teamTotalScores", result(teamB).get("totalScores"));
        responseResultB.put("teamBowlingStat", bowlerWicketsDetails(teamB).get("bowler"));
        responseResultB.put("totalWickets", bowlerWicketsDetails(teamB).get("totalWickets"));

        responseResultB.put("teamId", teamB.getId());
        responseResultB.put("teamName", teamB.getName());
        teamAScores.put("team2", responseResultB);

        //Total overs bowled and total wicket taken

        int difference = 0;
        StringBuilder stringBuilder = new StringBuilder();
        int teamAResult = (int) result(teamA).get("totalScores");
        int teamBResult = (int) result(teamB).get("totalScores");

       // difference = teamAResult - teamBResult;

        String result = Optional.of(teamAResult - teamBResult)
                .filter(differences -> differences > 0)
                .map(differences -> "Team " + teamA.getName() + " Won the match by : " + differences + " runs")
                .orElseGet(() -> teamAResult == teamBResult ? "Match Drawn!" : "Team " + teamA.getName() + " Won the match by : " + Math.abs(teamAResult - teamBResult) + " runs");

        stringBuilder.append(result);
        teamAScores.put("matchResult", stringBuilder);
        return teamAScores;
    }
}