package src.buisnesEntities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

// This class represents a game that can be bet on
public class Game implements Serializable {
    // ID of the game
    String gameID;
    // home team of the game
    Team home;
    // away team of the game
    Team away;
    // final score of the home team
    int home_score;
    // final score of the away team
    int away_score;
    //final date of the game
    Date final_date;
    // game name, a string composed of home team name and away team name
    String name;
    // List of bets placed on this game
    ArrayList<Bet> bets;

    // default constructor
    public Game() {
    }

    /**
     * constructor to create a new game
     * @param gameID
     * @param home home team
     * @param away away team
     * @param fd final date
     */
    public Game(String gameID, Team home, Team away, Date fd) {
        this.gameID = gameID;
        this.home = home;
        this.away = away;
        //initialize scores to 0
        this.away_score = 0;
        this.home_score = 0;
        this.final_date = fd;
        //create name of the game
        this.name = home.getName() + " VS " + away.getName();
        //initialize empty list of bets
        this.bets = new ArrayList<Bet>();
    }

    // getters and setters for all class properties
    public String getName() {
        return name;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public Team getAway() {
        return away;
    }

    public void setAway(Team away) {
        this.away = away;
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

    public Date getFinal_date() {
        return final_date;
    }

    public void setFinal_date(Date final_date) {
        this.final_date = final_date;
    }

    public ArrayList<Bet> getBets() {
        return bets;
    }

    /**
     * Method to set the final score of the game
     * @param h home team
     * @param a away team
     */
    public void final_score(int h, int a) {
        this.home_score = h;
        this.away_score = a;
    }

    // Method to convert the object to a hashmap
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> toReturn = new HashMap<>();
        toReturn.put("gameId", gameID);
        toReturn.put("home", home.getTeamID());
        toReturn.put("away", away.getTeamID());
        toReturn.put("home_score", home_score);
        toReturn.put("away_score", away_score);
        toReturn.put("final_date", final_date.toString());
        toReturn.put("bets", bets);
        return toReturn;
    }

    // Method to convert the object to a string
    public String toString() {
        return gameID;
    }
}


