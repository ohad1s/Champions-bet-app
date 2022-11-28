package src.games;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private ImageView image;
    private List<Game> historyGames;

    public  Team() {
        historyGames = new ArrayList<Game>();
    }
}
