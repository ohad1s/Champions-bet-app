package src.games;

import java.util.HashMap;

public class Bet {
    private  String betID; //Should be a player ID
    private String gameId;
    private String name;
    private int home_score;
    private int away_score;

    public Bet(String betID, int h, int a,   String game){
        this.betID = betID;
        this.gameId = game;
        this.home_score = h;
        this.away_score = a;
        this.name= home_score+" - "+away_score;
    }
    public Bet(){}

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

    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> toReturn = new HashMap<>();
        toReturn.put("betID", betID);
        toReturn.put("gameId", gameId);
        toReturn.put("home_score", home_score);
        toReturn.put("away_score", away_score);
        toReturn.put("name", this.name);
        return toReturn;
    }
    public String toString() {
        return betID;
    }
}
