package src.games;

import java.util.HashMap;

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
    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> toReturn = new HashMap<>();
        toReturn.put("game", game.toHashMap());
        toReturn.put("user", user.getEmail());
        toReturn.put("home_score", home_score);
        toReturn.put("away_score", away_score);
        return toReturn;
    }
}
