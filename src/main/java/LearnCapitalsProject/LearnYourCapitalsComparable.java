package LearnCapitalsProject;

//sortere tekstfila, for å kunne lage leaderboard
public class LearnYourCapitalsComparable implements Comparable<LearnYourCapitalsComparable>{

    private String continent;
    private String username;
    private final int totalGuesses;

    public LearnYourCapitalsComparable(String continent, String username, int totalGuesses) {
        this.continent = continent;
        this.username = username;
        this.totalGuesses = totalGuesses;
    }

    public String getContinent() {
        return continent;
    }

    public String getUsername() {
        return username;
    }

    public int getTotalguesses() {
        return totalGuesses;
    }

    @Override
    public int compareTo(LearnYourCapitalsComparable other) {
        return Integer.compare(this.totalGuesses, other.totalGuesses);
    }

    @Override
    public String toString() {
        return username + ": " + totalGuesses + " guesses";
    }
    
}
