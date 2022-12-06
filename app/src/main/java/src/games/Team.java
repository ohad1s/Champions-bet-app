package src.games;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Team {
    private String teamID;
    private String name;
    private ImageView image;
    private List<Game> historyGames;

    public Team(String teamID, String name /*ImageView image*/) {
        this.teamID = teamID;
        this.name = name;
        //this.image = image;
        this.historyGames = new ArrayList<Game>();
    }
    public Team(){}
    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hash = new HashMap<>();
        hash.put("teamID", teamID);
        hash.put("team_name", name);
        hash.put("team_img", image);
        return hash;
    }
    public String toString() {
        return teamID;
    }
}
