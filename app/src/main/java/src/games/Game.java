package src.games;

import java.util.ArrayList;

public class Game {
    String home;
    String away;
    int home_score;
    int away_score;
    int points;
    ArrayList<Bet>bets;

    public Game(String home, String away, int p){
        this.home=home;
        this.away=away;
        this.points=p;
        this.away_score=-1;
        this.home_score=-1;
        bets= new ArrayList<Bet>();
    }

    public void final_score(int h, int a){
        this.home_score=h;
        this.away_score=a;
    }
}
