package src.games;

import java.util.HashMap;

public class Bet {
    private  String betID; //Should be a player ID
    private Game game;
    private String name;
    private int home_score;
    private int away_score;
    User user;

    public Bet(String betID, User user, int h, int a, Game game){
        this.betID = betID;
        this.game = game;
        this.user = user;
        this.home_score = h;
        this.away_score = a;
        this.name= game.home.getName()+" VS "+game.away.getName();
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> toReturn = new HashMap<>();
        toReturn.put("betID", betID);
        toReturn.put("game", game.getGameID());
        toReturn.put("home_score", home_score);
        toReturn.put("away_score", away_score);
        toReturn.put("name", this.name);
        return toReturn;
    }
    public String toString() {
        return betID;
    }
}
