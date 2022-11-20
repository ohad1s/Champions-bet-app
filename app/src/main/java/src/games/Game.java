package src.games;

import java.util.ArrayList;
import java.util.Date;

public class Game {
    int id;
    String home;
    String away;
    int home_score;
    int away_score;
    int points;
    Date final_date;
    ArrayList<Bet>bets;

    public Game(int id,String home, String away, int p, Date fd){
        this.id =id;
        this.home=home;
        this.away=away;
        this.points=p;
        this.away_score=-1;
        this.home_score=-1;
        this.final_date=fd;
        bets= new ArrayList<Bet>();
    }

    public void final_score(int h, int a){
        this.home_score=h;
        this.away_score=a;
    }
}
