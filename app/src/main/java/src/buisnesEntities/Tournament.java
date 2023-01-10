package src.buisnesEntities;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This class represents a tournament in the application
public class Tournament implements Serializable {
    // instance of FirebaseFirestore to access the Firestore database
    private FirebaseFirestore firebaseDatabase;
    // ID of the tournament
    private String tournamentID;
    // name of the tournament
    private String name;
    // ID of the manager of the tournament
    private String managerID;
    // end date of the tournament
    private Date end;
    // list of games that are in the tournament
    private List<Game> games;
    // list of participants in the tournament
    private List<String> participants;
    // leaderboard for the tournament
    private Leaderboard leaderboard;

    // default constructor
    public Tournament() {

    }

    /**
     * constructor
     * @param tournamentID
     * @param name
     * @param manager managerID
     * @param end date to end the tournament
     */
    public Tournament(String tournamentID, String name, String manager, Date end) {
        this();
        this.tournamentID = tournamentID;
        this.name = name;
        this.managerID = manager;
        this.end = end;
        this.games = new ArrayList<>();
        this.participants = new ArrayList<>();
        this.leaderboard = new Leaderboard();
    }


    //getters and setters
    public String getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(String tournamentID) {
        this.tournamentID = tournamentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManager(String managerID) {
        this.managerID = managerID;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<Game> getGames() {
        return games;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    // method to convert the object to a hashmap
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> toReturn = new HashMap<>();
        toReturn.put("tournamentID", tournamentID);
        toReturn.put("name", name);
        toReturn.put("manager", managerID);
        toReturn.put("end", end);
        toReturn.put("participants", participants.toString());
        toReturn.put("games", games);
        toReturn.put("leaderboard", leaderboard);
        return toReturn;
    }

    // toString method to display the object
    public String toString() {
        return tournamentID;
    }
}

