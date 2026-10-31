package LearnCapitolsProject;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import LearnCapitalsProject.LearnYourCapitalsComparable;
import LearnCapitalsProject.LearnYourCapitalsFileHandler;
import LearnCapitalsProject.LearnYourCapitalsGame;

public class LearnYourCapitolsTest {

    private LearnYourCapitalsGame game;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setup() {
        String tempFile = tempDir.resolve("test_leaderboard.txt").toString();
        this.game = new LearnYourCapitalsGame(tempFile);
    }

    //teste filskriving
    @Test
    @DisplayName("filereader reads and writes data")
    public void readAndWriteToFile() {
        String filepath = tempDir.resolve("test.txt").toString();
        LearnYourCapitalsFileHandler fileHandler = new LearnYourCapitalsFileHandler(filepath);

        String[] player1 = {"Europe", "Amalie", "40"};
        String[] player2 = {"Europe", "Fleb", "32"};
        String[] player3 = {"Europe", "Emma", "22"};
        String[] player4 = {"Europe", "Frida", "30"};
        String[] player5 = {"Europe", "Benjamin", "50"};
        String[] player6 = {"Europe", "Sander", "59"};
    

        List<ArrayList<String>> originalleaderboard = new ArrayList<>();
        originalleaderboard.add(new ArrayList<>(Arrays.asList(player1)));
        originalleaderboard.add(new ArrayList<>(Arrays.asList(player2)));
        originalleaderboard.add(new ArrayList<>(Arrays.asList(player3)));
        originalleaderboard.add(new ArrayList<>(Arrays.asList(player4)));
        originalleaderboard.add(new ArrayList<>(Arrays.asList(player5)));
        originalleaderboard.add(new ArrayList<>(Arrays.asList(player6)));
        
        fileHandler.writeToFile(originalleaderboard);
        
        List<ArrayList<String>> loadedleaderboard = fileHandler.readFromFile();

        Assertions.assertEquals(originalleaderboard, loadedleaderboard);
    }


    //teste usernameValditation
    @Test
    @DisplayName("username is validated correctly")
    public void testValidateUsername() {
        Assertions.assertFalse(game.isValidUsername(null));
        Assertions.assertFalse(game.isValidUsername("  "));
        Assertions.assertFalse(game.isValidUsername(""));
        Assertions.assertTrue(game.isValidUsername("Amalie"));
    }

    //teste comparable
    @Test
    @DisplayName("Comparable sorts correctly")
    public void testSortLeaderboard() {
        List<LearnYourCapitalsComparable> players = new ArrayList<>();
        players.add(new LearnYourCapitalsComparable("Europe", "Amelie", 20));
        players.add(new LearnYourCapitalsComparable("Europe", "Emma", 2));
        players.add(new LearnYourCapitalsComparable("Europe", "frida", 200));

        Collections.sort(players);

        Assertions.assertEquals("Emma", players.get(0).getUsername());
        Assertions.assertEquals("Amelie", players.get(1).getUsername());
        Assertions.assertEquals("frida", players.get(2).getUsername());
    }

    //teste hints gir +2 gjett
    @Test
    @DisplayName("hint increases wrong guess by 2")
    public void testGetHint() {
        game.startGame("Europe", "Amalie");
        game.getNextHint();
        game.getNextHint();
        game.getNextHint();
        int totalGuesses = game.getTotalGuesses();

        Assertions.assertEquals(6, totalGuesses);
    }

    //teste isGuessValid
    @Test
    @DisplayName("wrong guess increase totalGuesses by 1")
    public void testValidGuess() {
        game.startGame("Europe", "Amalie");
        game.setCurrentCapitalForTesting("Oslo");

        game.isGuessValid(Arrays.asList("X", "X", "X", "X"));
        Assertions.assertEquals(1, game.getTotalGuesses());

        boolean correctGuess = game.isGuessValid(Arrays.asList("O", "s", "l", "o"));
        Assertions.assertTrue(correctGuess);
    }

    //teste gameisfinished
    @Test
    @DisplayName("game ends when all capitals are guessed")
    public void testIsGameOver() {
        game.startGame("Oceania", "Amalie");

        Assertions.assertFalse(game.isGameOver());

        while (!game.isGameOver()) {
            game.loadNextCountry();
        }

        Assertions.assertTrue(game.isGameOver());
    }
    
}
