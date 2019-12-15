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
}
