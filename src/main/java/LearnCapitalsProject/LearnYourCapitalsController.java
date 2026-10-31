package LearnCapitalsProject;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LearnYourCapitalsController {

    @FXML private TextField userNameField;
    @FXML private Label overlayContinentLabel;
    @FXML private StackPane overlayChosenContinent;
    @FXML private Button capitalButton;
    @FXML private VBox overlayUsernameView, overlayLeaderboardView, overlayChoiceView;
    @FXML private ListView<String> leaderboardList;

    //til game-overlay:
    @FXML private StackPane overlayGameView;
    @FXML private Label countryLabel, progressLabel, feedbackLabel, guessCountryLabel;
    @FXML private HBox letterBoxContainer; 

    private LearnYourCapitalsGame game;
    private List<TextField> letterFields = new ArrayList<>(); //de dynamiske boksene
    private String continent;
    private static final String filepath = "Leaderboard.txt"; 

    public LearnYourCapitalsController() {
        this.game = new LearnYourCapitalsGame(filepath);
    }

    @FXML
    private void handleContinentClick(ActionEvent event) {
        
        this.continent = ((Button) event.getSource()).getText();
        overlayContinentLabel.setText(continent);
        game.setContinent(continent);

        overlayChosenContinent.setManaged(true);
        overlayChosenContinent.setVisible(true);
        overlayChoiceView.setManaged(true); //skal disse være her idk..
        overlayChoiceView.setVisible(true); //samme her 
    }

    //dette er valg som skjer i overlayet etter valgt kontinent! 
    @FXML
    public void handleNewGame(ActionEvent event) { //ved new game skal et tekstfelt komme opp hvor man kan skrive inn brukernavn
        showView(overlayUsernameView);
    }

    @FXML
    public void handleStartGame(ActionEvent event) {
        String username = userNameField.getText();
        //validering av brukernavn:
        if (!game.isValidUsername(username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("invalid username");
            alert.setHeaderText(null);
            alert.setContentText("username cannot be empty");
            alert.show();
            //bytta ut feedbacklabel med alert boks
            return;
        }

        game.startGame(continent, username);
        showGameView();
        loadCurrentRound();
    }

    private void showGameView() {
        overlayChosenContinent.setVisible(false);
        overlayChosenContinent.setManaged(false);
        overlayGameView.setVisible(true);
        overlayGameView.setManaged(true);
    }

    private void loadCurrentRound() {
        countryLabel.setText("What is the capital of " + game.getCurrentCountry() + "?");
        progressLabel.setText("Country " + (game.getCountryCounter() - game.getRemainingCountries())
                              + " of " + game.getCountryCounter());
        feedbackLabel.setText("");
        guessCountryLabel.setText("Wrong guesses: " + game.getTotalGuesses());

        buildLetterBoxes(game.getCapitalLength());
    }

    private void buildLetterBoxes(int length) {
        letterBoxContainer.getChildren().clear(); // fjern gamle bokser
        letterFields.clear();

        char[] letters = game.getCapitalLetters();

        for (int i = 0; i < length; i++) {
            // Mellomrom og bindestrek: vis som label, ikke tekstboks
            if (letters[i] == ' ' || letters[i] == '-') {
                Label spacer = new Label(String.valueOf(letters[i]));
                spacer.setStyle("-fx-font-size: 18px; -fx-padding: 0 5;");
                letterBoxContainer.getChildren().add(spacer);
                letterFields.add(null); // placeholder for indeks
            } else {
                TextField tf = new TextField();
                tf.setPrefWidth(35);
                tf.setPrefHeight(40);
                tf.setAlignment(Pos.CENTER);
                tf.setStyle("-fx-font-size: 16px; -fx-border-color: #25bad8; -fx-border-radius: 4;");

                // Auto-hopp til neste boks når bruker skriver én bokstav
                final int index = i;
                tf.textProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal.length() > 1) tf.setText(newVal.substring(newVal.length() - 1));
                    if (newVal.length() == 1) jumpToNextField(index);
                });

                letterBoxContainer.getChildren().add(tf);
                letterFields.add(tf);
            }
        }
    }

    private void jumpToNextField(int currentIndex) {
        for (int i = currentIndex + 1; i < letterFields.size(); i++) {
            if (letterFields.get(i) != null && letterFields.get(i).getText().isEmpty()) {
                letterFields.get(i).requestFocus();
                return;
            }
        }
    }

    @FXML
    private void handleGuess(ActionEvent event) {
        // Valider at alle bokser er fylt ut
        List<String> userInput = new ArrayList<>();
        for (TextField tf : letterFields) {
            if (tf == null) {
                userInput.add(" "); // mellomrom
            } else if (tf.getText().isEmpty()) {
                feedbackLabel.setText("Fill in all boxes first!");
                return;
            } else {
                userInput.add(tf.getText());
            }
        }

        boolean correct = game.isGuessValid(userInput);
        guessCountryLabel.setText("Wrong guesses: " + game.getTotalGuesses());

        if (correct) {
            feedbackLabel.setText("✓ Correct!");
            if (game.isGameOver()) {
                endGame();
            } else {
                game.loadNextCountry();
                loadCurrentRound();
            }
        } else {
            feedbackLabel.setText("✗ Wrong, try again!");
        }
    }

    @FXML
    private void handleHint(ActionEvent event) {
        int hintIndex = game.getHintsGiven();
        Character hint = game.getNextHint();

        if (hint == null) {
            feedbackLabel.setText("no more hints");
            return;
        }

        if (hintIndex < letterFields.size() && letterFields.get(hintIndex) != null) {
            TextField tf = letterFields.get(hintIndex);
            tf.setText(String.valueOf(hint));
            tf.setDisable(true);
        }

        guessCountryLabel.setText("Wrong guesses: " + game.getTotalGuesses());
    }

    private void endGame() {
        game.writeToFile();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game confirmation");
        alert.setContentText("Game done! Total wrong guesses: " + game.getTotalGuesses());
        alert.show();
        handleGoBack();
        handleCloseOverlay();
    }

    @FXML
    private void handleQuitGame(ActionEvent event) {
        overlayGameView.setVisible(false);
        overlayGameView.setManaged(false);
        overlayChosenContinent.setVisible(false);
        overlayChosenContinent.setManaged(false);
    }

    @FXML
    private void handleLeaderboard(ActionEvent event) {
        leaderboardList.getItems().clear();
        var players = game.getLeaderboardForContinent();

        if (players.isEmpty()) {
            leaderboardList.getItems().add("No scores yet!");
        } else {
            int rank = 1;
            for (var player : players) {
                leaderboardList.getItems().add(
                    rank++ + ". " + player.getUsername() + " — " + player.getTotalguesses() + " guesses"
                );
            }
        }
        showView(overlayLeaderboardView);
    }

    @FXML private void handleGoBack(ActionEvent event) {showView(overlayChoiceView); }
    @FXML private void handleCloseOverlay(ActionEvent event) { overlayChosenContinent.setManaged(false); overlayChosenContinent.setVisible(false); }

    private void showView(VBox viewToShow) {
        overlayChoiceView.setVisible(false); overlayChoiceView.setManaged(false);
        overlayUsernameView.setVisible(false); overlayUsernameView.setManaged(false);
        overlayLeaderboardView.setVisible(false); overlayLeaderboardView.setManaged(false);
        if (overlayGameView != null) { overlayGameView.setVisible(false); overlayGameView.setManaged(false); }

        viewToShow.setVisible(true);
        viewToShow.setManaged(true);
    }
    
}
