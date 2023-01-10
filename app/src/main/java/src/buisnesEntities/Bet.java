package src.buisnesEntities;

import java.io.Serializable;
import java.util.HashMap;

// This class represents a bet made by a player on a game
public class Bet implements Serializable {
    // ID of the player that made the bet
    private String betID;
    // ID of the game the bet is placed on
    private String gameId;
    // Score prediction name, a string composed of home_score and away_score
    private String name;
    // predicted home team score
    private int home_score;
    //predicted away team score
    private int away_score;

    // default constructor
    public Bet() {
    }

    /**
     * constructor to create a new bet
     * @param betID
     * @param h     home team
     * @param a     away team
     * @param game  gameID
     */
    public Bet(String betID, int h, int a, String game) {
        this.betID = betID;
        this.gameId = game;
        this.home_score = h;
        this.away_score = a;
        //create name of the score prediction
        this.name = home_score + " - " + away_score;
    }

    // getters and setters for all class properties
    public String getName() {
        return name;
    }

    public String getBetID() {
        return betID;
    }

    public void setBetID(String betID) {
        this.betID = betID;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String game) {
        this.gameId = game;
    }

    public int getHome_score() {
        return home_score;
    }

    public void setHome_score(int home_score) {
        this.home_score = home_score;
    }

    public int getAway_score() {
        return away_score;
    }

    public void setAway_score(int away_score) {
        this.away_score = away_score;
    }

    // Method to convert the object to a hashmap
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> toReturn = new HashMap<>();
        toReturn.put("betID", betID);
        toReturn.put("gameId", gameId);
        toReturn.put("home_score", home_score);
        toReturn.put("away_score", away_score);
        toReturn.put("name", this.name);
        return toReturn;
    }

    // Method to convert the object to a string
    public String toString() {
        return betID;
    }
}
