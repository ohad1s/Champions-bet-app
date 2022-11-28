package src.games;

public class Bet {
    private Game game;
    private int home_score;
    private int away_score;
    User user;

    public Bet(User user, int h, int a, Game game){
        this.game = game;
        this.user = user;
        this.home_score = h;
        this.away_score = a;
    }

}
