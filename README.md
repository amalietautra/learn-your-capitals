# Learn Your Capitals 🌍

A desktop quiz app for learning the capitals of the world, built with **Java** and **JavaFX**.

I made it because I wanted to get better at capitals myself, so it is designed for *learning* rather than just testing: you can ask for hints letter by letter instead of getting stuck.

![Screenshot of the game](docs/screenshot.png)

## Features

- **Choose a continent**: Europe, Asia, Africa, North America, South America or Oceania (170+ countries in total).
- **One country at a time, in random order.** You type the capital into one text box per letter, so you always know how long the answer is.
- **Hints:** "Get hint" reveals the next letter (first, then second, and so on). Each hint costs two extra guesses.
- **Leaderboard** per continent showing the top 5 players with the fewest wrong guesses, saved to file between sessions.
- **Username validation** before the game starts.

## Tech

| | |
|---|---|
| Language | Java 21 |
| UI | JavaFX 23, FXML designed in Scene Builder |
| Build | Maven |
| Testing | JUnit 5 |

## Architecture (MVC)

The app follows the Model–View–Controller pattern:

- **View:** FXML files (`src/main/resources/LearnCapitalsProjectFXML/`) define the layout. The letter boxes are generated dynamically based on the length of each capital.
- **Controller:** `LearnYourCapitalsController` handles user input and updates the view. Game logic is delegated to the model rather than living in the controller.
- **Model:**
  - `LearnYourCapitalsGame`: game state, shuffling, guess checking, hints and game-over logic
  - `LearnYourCapitalsFileHandler`: reads and writes the leaderboard (`continent;username;guesses`)
  - `LearnYourCapitalsComparable`: implements `Comparable` to sort players for the leaderboard

## Tests

JUnit tests cover the most critical parts of the logic:

- writing to and reading from the leaderboard file (round trip)
- username validation
- leaderboard sorting
- guess counting and correct/incorrect guesses
- the game ending once every country on the continent has been shown

## Run it

Requires Java 21+ and Maven.

```bash
mvn javafx:run   # start the app
mvn test         # run the tests
```

## Background

A solo project for TDT4100 Object-Oriented Programming at NTNU, spring 2026.
