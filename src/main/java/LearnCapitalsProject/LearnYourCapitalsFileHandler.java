package LearnCapitalsProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LearnYourCapitalsFileHandler {

    private final String filepath;

    //når username er laget; sjekke om den er i fila fra før (hvis continent == continent) - hvis ikke; 
    //opprette ny linje for spiller. 
    //når runde ferdig, skrive til fil slik:
    //      continent;username;totalGuess
    //      må lage comparable for å sortere lista og gjøre om til et leaderboard

    public LearnYourCapitalsFileHandler(String filepath){
        if (filepath == null || filepath.isBlank()) {
            throw new IllegalArgumentException("filepath cannot be null or empty!");
        }
        this.filepath = filepath;
    }

    //skirver én rad: "continent;username;totalGuesses"
    public void writeToFile(List<ArrayList<String>> leaderboard) { //kalle på denne i Game, når alle landene er rundet //grunnen til List formatet kan man forklare ved at hver linje i tekstfila er en egen ArrayList
        try (FileWriter fileWriter = new FileWriter(this.filepath)) {
            for (ArrayList<String> entry : leaderboard) {
                fileWriter.write(String.join(";", entry));
                fileWriter.write("\n");
            }
        } catch (IOException e) {
            // fil kunne ikke skrives
        }
    }

    public List<ArrayList<String>> readFromFile() {
        List<ArrayList<String>> leaderboard = new ArrayList<>();
        try (BufferedReader br = new BufferedReader (new FileReader(filepath))){
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(";");
                leaderboard.add(new ArrayList<>(Arrays.asList(parts)));
            }
        } catch (IOException e) {
            // fil finnes ikke ennå, returnerer tom liste
        }
        return leaderboard;
    }
}
