package src.games;

import src.participants.User;

public class Bet {
    int home_score;
    int away_score;
    User user;

    public Bet(User user, int h, int a){
        this.user=user;
        this.home_score=h;
        this.away_score=a;
    }

}
