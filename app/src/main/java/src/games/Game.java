package src.games;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Game {
    String gameID; //Should be random or from 1 to infinity by order
    Team home;
    Team away;
    int home_score;
    int away_score;
    Date final_date;
    String name;
    ArrayList<Bet>bets;

    public Game(String gameID, Team home, Team away, Date fd) {
        this.gameID = gameID;
        this.home=home;
        this.away=away;
        this.away_score=0;
        this.home_score=0;
        this.final_date=fd;
        this.name= home.getName()+" VS "+away.getName();
        this.bets= new ArrayList<Bet>();
    }
    public Game(){}

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

    public void final_score(int h, int a){
        this.home_score=h;
        this.away_score=a;
    }
    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> toReturn = new HashMap<>();
        toReturn.put("gameId", gameID);
        toReturn.put("home", home.getTeamID());
        toReturn.put("away", away.getTeamID());
        toReturn.put("home_score", home_score);
        toReturn.put("away_score", away_score);
        toReturn.put("final_date", final_date.toString());
        return toReturn;
    }
    public String toString() {
        return gameID;
    }
}
