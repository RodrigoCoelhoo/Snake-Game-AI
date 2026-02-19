package data;
import java.io.*;
import java.util.*;

public class Leaderboard {
    private final String leaderboardFile;
    private final List<Player> players;
    private int topN = 10;

    /**
     * @param leaderboardFile Represents the name of the file containing the leaderboard
     */
    public Leaderboard(String leaderboardFile) {
        this.leaderboardFile = leaderboardFile;
        this.players = new ArrayList<>();
        loadLeaderboard();
    }

    
    /** 
     * @return String
     */
    public String getFile() {
        return leaderboardFile;
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Add a player to the leaderboard or update it if the player is already in the leaderboard
     * @param name  Represents the name of the player
     * @param score Represents the score of the player
     * @param time  Represents the time of the player in milliseconds
     */
    public void addEntry(String name, int score, long time) {
        boolean playerExists = false;
        for (Player player : players) {
            if (player.getName().equals(name)) {
                if (player.getScore() < score) {
                    player.setScore(score);
                    player.setTime(new Time(time));
                }
                playerExists = true;
                break;
            }
        }

        if (!playerExists) {
            Player newPlayer = new Player(name, score, new Time(time));
            players.add(newPlayer);
        }

        sortLeaderboard();
        saveLeaderboard();
    }

    /**
     * Responsible for re-writing the leaderboard txt file
     */
    private void saveLeaderboard() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(leaderboardFile))) {
            writer.write("#\tPlayer Name\t\tScore\tTime");
            writer.newLine();
            int count = 1;
            int topN = this.topN;
            for (Player player : players) {
                writer.write(count + "\t" + player.toString());
                writer.newLine();
                count++;
                if (count > topN) break;
            }
        } catch (IOException e) {
            System.err.println("Falha ao salvar o leaderboard: " + e.getMessage());
        }
    }

    /**
     * Responsible for reading the leaderboard from the txt file and loading the List of players
     */
    private void loadLeaderboard() {
        try (BufferedReader reader = new BufferedReader(new FileReader(leaderboardFile))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 4) {
                    String name = parts[1].trim();
                    int score = Integer.parseInt(parts[2].trim());
                    String timeString = parts[3].trim();
                    players.add(new Player(name, score, new Time(timeString)));
                }
            }
            sortLeaderboard();
        } catch (IOException e) {
            System.err.println("Falha ao carregar a leaderboard: " + e.getMessage());
        }
    }

    /**
     * Responsible for sorting the leaderboard
     */
    private void sortLeaderboard() {
        players.sort((p1, p2) -> {
            if (p1.score != p2.score) {
                return Integer.compare(p2.score, p1.score);
            } else {
                return Long.compare(p1.time.getMilliseconds(), p2.time.getMilliseconds()); 
            }
        });
    }

    public List<Player> getTopN(int n) {
        List<Player> result = new ArrayList<Player>();
        sortLeaderboard();

        n = n < this.players.size() ? n : this.players.size();

        for (int i = 0; i < n; i++) {
            result.add(this.players.get(i));
        }

        return result;
    }

    public class Player {
        private final String name;
        private int score;
        private Time time;

        private Player(String name, int score, Time time) {
            this.name = name;
            this.score = score;
            this.time = time;
        }

        @Override
        public String toString() {
            return name + "\t" + score + "\t" + time.formatTime();
        }

        public String getName() {
            return this.name;
        }

        public int getScore() {
            return this.score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public Time getTime() {
            return this.time;
        }

        public void setTime(Time time) {
            this.time = time;
        }
    }
}
