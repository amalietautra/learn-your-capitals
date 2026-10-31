package LearnCapitalsProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LearnYourCapitalsGame {

    private LearnYourCapitalsFileHandler fileHandler;

    //data til selve spillet
    //private List<ArrayList<String>> leaderboardInformation;
    //private LearnYourCapitalsComparable comparable;
    private String currentContinent;
    private String username;

    private List<String> allCountries; //alle land i kontinentet, blandet
    private int countryIndex = 0;       //hvilket land vi er på nå

    private String currentCountry;
    private String currentCapital; //hovedstaten som skal gjettes nå
    private char[] capitalLetters; //liste av bokstavene i currentCapital

    private int countryCounter;     //tot antall Country i currentContinent
    private int remainingCountries; //countryCounter --; for hvert korrekt gjett
    
    private int totalGuesses;   //tot gjett
    private int currentGuesses; //gjett på currentCountry
    private int hintsGiven;     //hvor mange hint som er gitt (kan ikke gi mer enn bokstaver i ordet)
    //private int remainingHints; 

    private static final Map<String, Map<String, String>> world = Map.ofEntries(  //her har vi da en hashmap med flere ofEntries-par for hvert land i hvert kontinent. 

    Map.entry("Europe", Map.ofEntries(
        Map.entry("Norway", "Oslo"),Map.entry("Sweden", "Stockholm"),Map.entry("Denmark", "Copenhagen"),Map.entry("Finland", "Helsinki"),Map.entry("Iceland", "Reykjavik"),Map.entry("Germany", "Berlin"),Map.entry("France", "Paris"),Map.entry("Spain", "Madrid"),Map.entry("Portugal", "Lisbon"),Map.entry("Italy", "Rome"),Map.entry("Greece", "Athens"),Map.entry("Netherlands", "Amsterdam"),Map.entry("Belgium", "Brussels"),Map.entry("Switzerland", "Bern"),Map.entry("Austria", "Vienna"),Map.entry("Poland", "Warsaw"),Map.entry("Czech Republic", "Prague"),Map.entry("Slovakia", "Bratislava"),Map.entry("Hungary", "Budapest"),
        Map.entry("Romania", "Bucharest"),Map.entry("Bulgaria", "Sofia"),Map.entry("Serbia", "Belgrade"),Map.entry("Croatia", "Zagreb"),Map.entry("Slovenia", "Ljubljana"),Map.entry("Bosnia and Herzegovina", "Sarajevo"),Map.entry("Albania", "Tirana"),Map.entry("North Macedonia", "Skopje"),Map.entry("Montenegro", "Podgorica"),Map.entry("Kosovo", "Pristina"),Map.entry("Lithuania", "Vilnius"),Map.entry("Latvia", "Riga"),Map.entry("Estonia", "Tallinn"),Map.entry("Belarus", "Minsk"),Map.entry("Ukraine", "Kyiv"),Map.entry("Moldova", "Chisinau"),Map.entry("Luxembourg", "Luxembourg City"),Map.entry("Malta", "Valletta"),Map.entry("Cyprus", "Nicosia"),Map.entry("Ireland", "Dublin"),Map.entry("England", "London"),Map.entry("Wales", "Cardiff"), Map.entry("Scotland", "Edinburgh"), Map.entry("Northern Ireland", "Belfast")
    )),

    Map.entry("Asia", Map.ofEntries(
        Map.entry("China", "Beijing"),Map.entry("Japan", "Tokyo"),Map.entry("South Korea", "Seoul"),Map.entry("North Korea", "Pyongyang"),Map.entry("Mongolia", "Ulaanbaatar"),Map.entry("India", "New Delhi"),Map.entry("Pakistan", "Islamabad"),Map.entry("Bangladesh", "Dhaka"),Map.entry("Sri Lanka", "Colombo"),Map.entry("Nepal", "Kathmandu"),Map.entry("Bhutan", "Thimphu"),Map.entry("Afghanistan", "Kabul"),Map.entry("Iran", "Tehran"),Map.entry("Iraq", "Baghdad"),Map.entry("Syria", "Damascus"),Map.entry("Lebanon", "Beirut"),Map.entry("Jordan", "Amman"),Map.entry("Israel", "Jerusalem"),Map.entry("Saudi Arabia", "Riyadh"),Map.entry("Yemen", "Sanaa"),Map.entry("Oman", "Muscat"),Map.entry("United Arab Emirates", "Abu Dhabi"),
        Map.entry("Qatar", "Doha"),Map.entry("Bahrain", "Manama"),Map.entry("Kuwait", "Kuwait City"),Map.entry("Turkey", "Ankara"),Map.entry("Georgia", "Tbilisi"),Map.entry("Armenia", "Yerevan"),Map.entry("Azerbaijan", "Baku"),Map.entry("Kazakhstan", "Astana"),Map.entry("Uzbekistan", "Tashkent"),Map.entry("Turkmenistan", "Ashgabat"),Map.entry("Tajikistan", "Dushanbe"),Map.entry("Kyrgyzstan", "Bishkek"),Map.entry("Vietnam", "Hanoi"),Map.entry("Thailand", "Bangkok"),Map.entry("Cambodia", "Phnom Penh"),
        Map.entry("Laos", "Vientiane"),Map.entry("Myanmar", "Naypyidaw"),Map.entry("Malaysia", "Kuala Lumpur"),Map.entry("Singapore", "Singapore"),Map.entry("Indonesia", "Jakarta"),Map.entry("Philippines", "Manila"),Map.entry("Taiwan", "Taipei")
    )),

    Map.entry("Africa", Map.ofEntries(
        Map.entry("Nigeria", "Abuja"),Map.entry("Ethiopia", "Addis Ababa"),Map.entry("Egypt", "Cairo"),Map.entry("DR Congo", "Kinshasa"),Map.entry("South Africa", "Pretoria"),Map.entry("Tanzania", "Dodoma"),Map.entry("Kenya", "Nairobi"),Map.entry("Uganda", "Kampala"),Map.entry("Algeria", "Algiers"),Map.entry("Sudan", "Khartoum"),Map.entry("Morocco", "Rabat"),Map.entry("Angola", "Luanda"),Map.entry("Mozambique", "Maputo"),Map.entry("Ghana", "Accra"),
        Map.entry("Madagascar", "Antananarivo"),Map.entry("Cameroon", "Yaounde"),Map.entry("Ivory Coast", "Yamoussoukro"),Map.entry("Niger", "Niamey"),Map.entry("Mali", "Bamako"),Map.entry("Burkina Faso", "Ouagadougou"),Map.entry("Malawi", "Lilongwe"),Map.entry("Zambia", "Lusaka"),Map.entry("Senegal", "Dakar"),Map.entry("Zimbabwe", "Harare"),Map.entry("Chad", "N'Djamena"),Map.entry("Somalia", "Mogadishu"),Map.entry("Rwanda", "Kigali"),Map.entry("Benin", "Porto-Novo"),
        Map.entry("Burundi", "Gitega"),Map.entry("Tunisia", "Tunis"),Map.entry("Libya", "Tripoli"),Map.entry("Togo", "Lome"),Map.entry("Sierra Leone", "Freetown"),Map.entry("Eritrea", "Asmara"),Map.entry("Namibia", "Windhoek"),Map.entry("Botswana", "Gaborone"),Map.entry("Lesotho", "Maseru"),Map.entry("Eswatini", "Mbabane"),Map.entry("Mauritius", "Port Louis"),Map.entry("Gabon", "Libreville")
    )),

    Map.entry("North America", Map.ofEntries(
        Map.entry("United States", "Washington D.C."),Map.entry("Canada", "Ottawa"),Map.entry("Mexico", "Mexico City"),Map.entry("Guatemala", "Guatemala City"),Map.entry("Belize", "Belmopan"),Map.entry("Honduras", "Tegucigalpa"),Map.entry("El Salvador", "San Salvador"),Map.entry("Nicaragua", "Managua"),Map.entry("Costa Rica", "San Jose"),Map.entry("Panama", "Panama City"),
        Map.entry("Cuba", "Havana"),Map.entry("Jamaica", "Kingston"),Map.entry("Haiti", "Port-au-Prince"),Map.entry("Dominican Republic", "Santo Domingo"),Map.entry("Trinidad and Tobago", "Port of Spain"),Map.entry("Bahamas", "Nassau"),Map.entry("Barbados", "Bridgetown"),Map.entry("Grenada", "St. George's"),Map.entry("Saint Lucia", "Castries"),Map.entry("Saint Vincent and the Grenadines", "Kingstown")
    )),

    Map.entry("South America", Map.ofEntries(
        Map.entry("Brazil", "Brasilia"),Map.entry("Argentina", "Buenos Aires"),Map.entry("Colombia", "Bogota"),Map.entry("Chile", "Santiago"),Map.entry("Peru", "Lima"),Map.entry("Venezuela", "Caracas"),
        Map.entry("Ecuador", "Quito"),Map.entry("Bolivia", "Sucre"),Map.entry("Paraguay", "Asuncion"),Map.entry("Uruguay", "Montevideo"),Map.entry("Guyana", "Georgetown"),Map.entry("Suriname", "Paramaribo")
    )),

    Map.entry("Oceania", Map.ofEntries(
        Map.entry("Australia", "Canberra"),Map.entry("New Zealand", "Wellington"),Map.entry("Papua New Guinea", "Port Moresby"),Map.entry("Fiji", "Suva"),Map.entry("Solomon Islands", "Honiara"),Map.entry("Vanuatu", "Port Vila"),
        Map.entry("Samoa", "Apia"),Map.entry("Kiribati", "South Tarawa"),Map.entry("Tonga", "Nukualofa"),Map.entry("Micronesia", "Palikir"),Map.entry("Palau", "Ngerulmud"),Map.entry("Marshall Islands", "Majuro"),Map.entry("Nauru", "Yaren"),Map.entry("Tuvalu", "Funafuti")
    ))

    );

    public LearnYourCapitalsGame(String filepath){
        fileHandler = new LearnYourCapitalsFileHandler(filepath);
    }

        // I LearnYourCapitalsGame.java
    public boolean isValidUsername(String username) {
        return username != null && !username.isBlank();
    }

    public void startGame(String continent, String username) {
        this.currentContinent = continent;
        this.username = username;
        this.totalGuesses = 0;
        this.countryIndex = 0;

        Map<String, String> continentMap = world.get(continent); //ny Map for bare continentet
        allCountries = new ArrayList<>(continentMap.keySet());
        Collections.shuffle(allCountries);                      //random rekkefølge på landene!

        countryCounter = continentMap.size();
        remainingCountries = countryCounter;

        loadNextCountry();  //laste inn første land
    }

    public void loadNextCountry() {
        if (countryIndex >= allCountries.size()) {
            return;         
        }

        Map<String, String> continentMap = world.get(currentContinent);
        
        currentCountry = allCountries.get(countryIndex);
        currentCapital = continentMap.get(currentCountry);

        //lage character array
        capitalLetters = currentCapital.toCharArray();

        currentGuesses = 0;
        hintsGiven = 0;
        countryIndex++;
        remainingCountries--;
    }

    public boolean isGuessValid(List<String> userInput) {
         //slå sammen userInput til én String:
        String guess = String.join("", userInput);

        if (!guess.equalsIgnoreCase(currentCapital)) {
            totalGuesses++;
            currentGuesses++;
            return false;
        }

        return true;
    }

    public Character getNextHint() {
        if (hintsGiven >= capitalLetters.length) {
            return null; 
        }

        char hint = capitalLetters[hintsGiven];
        hintsGiven++;
        totalGuesses += 2;
        currentGuesses += 2;

        return hint;
    }

    public boolean isGameOver() {               
        return countryIndex >= allCountries.size(); 
    }

    public void writeToFile() {
        List<ArrayList<String>> leaderboard = fileHandler.readFromFile();

        ArrayList<String> newPlayer = new ArrayList<>();
        newPlayer.add(currentContinent);
        newPlayer.add(username);
        newPlayer.add(String.valueOf(totalGuesses));
        leaderboard.add(newPlayer);

        fileHandler.writeToFile(leaderboard);
    }

    public List<LearnYourCapitalsComparable> getLeaderboardForContinent() {
        List<ArrayList<String>> allPlayers = fileHandler.readFromFile();
        List<LearnYourCapitalsComparable> continentPlayers = new ArrayList<>();

        for (ArrayList<String> player : allPlayers) {
            String continent = player.get(0);
            if (continent.equals(currentContinent)) {
                String username = player.get(1);
                int totalGuesses = Integer.parseInt(player.get(2));
                continentPlayers.add(new LearnYourCapitalsComparable(continent, username, totalGuesses));
            }
        }
        Collections.sort(continentPlayers); //her brukes compareTo automatisk
        return continentPlayers.subList(0, Math.min(5, continentPlayers.size())); //top 5 players
    }

    public String getCurrentCountry()   { return currentCountry; }
    public String getCurrentCapital()   { return currentCapital; }
    public char[] getCapitalLetters()   { return capitalLetters; }
    public int getCapitalLength()       { return capitalLetters.length; }
    public int getCountryCounter()      { return countryCounter; }
    public int getRemainingCountries()  { return remainingCountries; }
    public int getTotalGuesses()          { return totalGuesses; }
    public int getCurrentGuesses()        { return currentGuesses; }
    public int getHintsGiven()          { return hintsGiven; }
    public String getContinent()        { return currentContinent; }
    public void setContinent(String c)  { this.currentContinent = c; }
    public void setUsername(String u)   { this.username = u; }

    public void setCurrentCapitalForTesting(String capital) {
        this.currentCapital = capital;
        this.capitalLetters = capital.toCharArray();
    }
}
