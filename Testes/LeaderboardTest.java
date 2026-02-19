import org.junit.jupiter.api.*;

import data.Leaderboard;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

class LeaderboardTest {
    private Leaderboard leaderboard;

    @Test
    public void testAddPlayer() {
        String filePath = "tempFile.txt";
        File file = new File(filePath);

        leaderboard = new Leaderboard(filePath);

        leaderboard.addEntry("player1", 0, 30000); 
        leaderboard.addEntry("player2", 100, 60000); 
        leaderboard.addEntry("player3", 10, 45000); 

        List<Leaderboard.Player> players = leaderboard.getPlayers();

        assertEquals(3, players.size());
        assertEquals("player2", players.get(0).getName());
        assertEquals("player3", players.get(1).getName());
        assertEquals("player1", players.get(2).getName());

        leaderboard.addEntry("player1", 500, 70000); 
        assertEquals(500, leaderboard.getPlayers().get(0).getScore());

        file.delete();
    }

    @Test
    public void testGetTopN() {
        String filePath = "tempFile.txt";
        File file = new File(filePath);

        leaderboard = new Leaderboard(filePath);

        leaderboard.addEntry("Alice", 200, 45000);
        leaderboard.addEntry("Bob", 150, 60000);
        leaderboard.addEntry("Charlie", 150, 30000); 

        List<Leaderboard.Player> topPlayers = leaderboard.getTopN(2);

        assertEquals(2, topPlayers.size());
        assertEquals("Alice", topPlayers.get(0).getName());
        assertEquals(200, topPlayers.get(0).getScore());
        assertEquals("Charlie", topPlayers.get(1).getName());
        assertEquals(150, topPlayers.get(1).getScore());

        file.delete();
    }

    @Test
    public void testSaveAndLoadLeaderboard() {
        String filePath = "tempFile.txt";
        File file = new File(filePath);

        leaderboard = new Leaderboard(filePath);

        leaderboard.addEntry("John", 150, 70000); 
        leaderboard.addEntry("Doe", 100, 55000); 

        Leaderboard newLeaderboard = new Leaderboard("tempFile.txt");

        List<Leaderboard.Player> topPlayers = newLeaderboard.getTopN(2);

        assertEquals(2, topPlayers.size());
        assertEquals("John", topPlayers.get(0).getName());
        assertEquals(150, topPlayers.get(0).getScore());
        assertEquals("Doe", topPlayers.get(1).getName());
        assertEquals(100, topPlayers.get(1).getScore());

        file.delete();
    }
}
