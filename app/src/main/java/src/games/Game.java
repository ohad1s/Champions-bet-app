package src.games;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Game {
    Team home;
    Team away;
    int home_score;
    int away_score;
    Date final_date;
    ArrayList<Bet>bets;

    public Game(Team home, Team away, Date fd) {
        this.home=home;
        this.away=away;
        this.away_score=0;
        this.home_score=0;
        this.final_date=fd;
        bets= new ArrayList<Bet>();
    }

    public void final_score(int h, int a){
        this.home_score=h;
        this.away_score=a;
    }
    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> toReturn = new HashMap<>();
        toReturn.put("home", home.getName());
        toReturn.put("away", away.getName());
        toReturn.put("home_score", home_score);
        toReturn.put("away_score", away_score);
        toReturn.put("final_date", final_date);
        toReturn.put("bets", bets);
        return toReturn;
    }
}
