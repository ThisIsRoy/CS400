//
// Title:           NBA Statistics
// Files:           Requires: N/A
// Course:          CS 400 Fall 19 2019
//
// Author:          Roy Sun
// Email:           rsun65@wisc.edu
// Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here.  Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         NONE
// Online Sources:  thenewboston Youtube
//                  stackoverflow
//                  oracle docs
//                  geeksforgeeks
//

import java.util.ArrayList;

public class NBAPlayer implements Comparable<NBAPlayer> {
    private String name;
    private String position;
    private int age;
    private String team;
    private int gamesPlayed;
    private int threePointGoals;
    private int twoPointGoals;
    private int freeThrowsMade;
    private int totalRebounds;
    private int assists;
    private int steals;
    private int blocks;
    private int turnovers;
    private int fouls;
    private int points;

    public NBAPlayer(
            String name,
            String position,
            int age,
            String team,
            int gamesPlayed,
            int threePointGoals,
            int twoPointGoals,
            int freeThrowsMade,
            int totalRebounds,
            int assists,
            int steals,
            int blocks,
            int turnovers,
            int fouls,
            int points) {
        this.name = name;
        this.position = position;
        this.age = age;
        this.team = team;
        this.gamesPlayed = gamesPlayed;
        this.threePointGoals = threePointGoals;
        this.twoPointGoals = twoPointGoals;
        this.freeThrowsMade = freeThrowsMade;
        this.totalRebounds = totalRebounds;
        this.assists = assists;
        this.steals = steals;
        this.blocks = blocks;
        this.turnovers = turnovers;
        this.fouls = fouls;
        this.points = points;
    }

    @Override
    public int compareTo(NBAPlayer player2) {
        return this.points - player2.points;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public int getAge() {
        return age;
    }

    public String getTeam() {
        return team;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getThreePointGoals() {
        return threePointGoals;
    }

    public int getTwoPointGoals() {
        return twoPointGoals;
    }

    public int getFreeThrowsMade() {
        return freeThrowsMade;
    }

    public int getTotalRebounds() {
        return totalRebounds;
    }

    public int getAssists() {
        return assists;
    }

    public int getSteals() {
        return steals;
    }

    public int getBlocks() {
        return blocks;
    }

    public int getTurnovers() {
        return turnovers;
    }

    public int getFouls() {
        return fouls;
    }

    public int getPoints() {
        return points;
    }

    public ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<String>();
        values.add(name);
        values.add(position);
        values.add(Integer.toString(age));
        values.add(team);
        values.add(Integer.toString(gamesPlayed));
        values.add(Integer.toString(threePointGoals));
        values.add(Integer.toString(twoPointGoals));
        values.add(Integer.toString(freeThrowsMade));
        values.add(Integer.toString(totalRebounds));
        values.add(Integer.toString(assists));
        values.add(Integer.toString(steals));
        values.add(Integer.toString(blocks));
        values.add(Integer.toString(turnovers));
        values.add(Integer.toString(fouls));
        values.add(Integer.toString(points));
        return values;
    }
}
