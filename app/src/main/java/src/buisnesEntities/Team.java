package src.buisnesEntities;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// This class represents a team in the application
public class Team implements Serializable {
    // ID of the team
    private String teamID;
    // name of the team
    private String name;
    // image of the team
    private ImageView image;
    // list of the games that the team played in the past
    private List<Game> historyGames;

    // default constructor
    public Team() {
    }

    /**
     * constructor
     *
     * @param teamID
     * @param name
     */
    public Team(String teamID, String name /*ImageView image*/) {
        this.teamID = teamID;
        this.name = name;
        //this.image = image;
        this.historyGames = new ArrayList<Game>();
    }

    // getters and setters
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

    // method to convert the object to a hashmap
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hash = new HashMap<>();
        hash.put("teamID", teamID);
        hash.put("team_name", name);
        hash.put("team_img", image);
        return hash;
    }

    // method to convert the object to a string
    public String toString() {
        return teamID;
    }
}
