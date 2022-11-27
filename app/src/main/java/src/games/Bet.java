package src.games;

public class Bet {
    int game_id;
    int home_score;
    int away_score;
    User user;

    public Bet(User user, int h, int a, int game_id){
        this.game_id=game_id;
        this.user=user;
        this.home_score=h;
        this.away_score=a;
    }

}
