import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class NBAPlayerReader {
    private TreapTree<NBAPlayer> tree = new TreapTree<NBAPlayer>();
    private static String filename = "NBA_Player_Stats_18_19.csv";

    public static TreapTree<NBAPlayer> parseCSV() {
        ArrayList<String> data = new ArrayList<String>();
        String[] values;
        NBAPlayer player;
        String name;
        String position;
        int age;
        String team;
        int gamesPlayed;
        int threePointGoals;
        int twoPointGoals;
        int freeThrowsMade;
        int totalRebounds;
        int assists;
        int steals;
        int blocks;
        int turnovers;
        int fouls;
        int points;
        File file = new File(filename);

        // trees
        TreapTree<NBAPlayer> playerTree = new TreapTree<NBAPlayer>();

        try {
            Stream<String> stream = Files.lines(Paths.get(filename));
            stream.forEach(data::add);
            data.remove(0); // remove column names

            for (String line : data) {
                values = line.split(",");

                // get values and create player
                name = values[1];
                name = name.split("\\\\")[0];
                position = values[2];
                age = Integer.parseInt(values[3]);
                team = values[4];
                gamesPlayed = Integer.parseInt(values[5]);
                threePointGoals = Integer.parseInt(values[11]);
                twoPointGoals = Integer.parseInt(values[14]);
                freeThrowsMade = Integer.parseInt(values[18]);
                totalRebounds = Integer.parseInt(values[23]);
                assists = Integer.parseInt(values[24]);
                steals = Integer.parseInt(values[25]);
                blocks = Integer.parseInt(values[26]);
                turnovers = Integer.parseInt(values[27]);
                fouls = Integer.parseInt(values[28]);
                points = Integer.parseInt(values[29]);
                player = new NBAPlayer(name, position, age, team, gamesPlayed,
                        threePointGoals, twoPointGoals,freeThrowsMade, totalRebounds, assists, steals,
                        blocks, turnovers, fouls, points);
                playerTree.insert(player);
            }
//            Scanner scanner = new Scanner(file);
//            data = scanner.nextLine();
//
//            while (scanner.hasNext()) {
//                data = scanner.nextLine();
//                values = data.split(",");
//
//                // get values and create player
//                name = values[1];
//                position = values[2];
//                age = Integer.parseInt(values[3]);
//                team = values[4];
//                gamesPlayed = Integer.parseInt(values[5]);
//                minutesPlayed = Integer.parseInt(values[7]);
//                fieldGoalsMade = Integer.parseInt(values[8]);
//                threePointGoals = Integer.parseInt(values[11]);
//                twoPointGoals = Integer.parseInt(values[14]);
//                freeThrowsMade = Integer.parseInt(values[18]);
//                totalRebounds = Integer.parseInt(values[23]);
//                assists = Integer.parseInt(values[24]);
//                steals = Integer.parseInt(values[25]);
//                blocks = Integer.parseInt(values[26]);
//                turnovers = Integer.parseInt(values[27]);
//                fouls = Integer.parseInt(values[28]);
//                points = Integer.parseInt(values[29]);
//                player = new NBAPlayer(name, position, age, team, gamesPlayed, minutesPlayed, fieldGoalsMade,
//                        threePointGoals, twoPointGoals,freeThrowsMade, totalRebounds, assists, steals,
//                        blocks, turnovers, fouls, points);
//            }
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getLocalizedMessage());
        }

        return playerTree;
    }

    public static void main(String[] args) {
        parseCSV();
    }
}
