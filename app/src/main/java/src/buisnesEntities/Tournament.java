package src.buisnesEntities;


import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Tournament implements Serializable {
    private FirebaseFirestore firebaseDatabase;
    private String tournamentID;
    private String name;
    private String managerID;
    private Date  end;
    private  List<Game> games;
    private  List<String> participants;

    public Tournament(String tournamentID, String name, String manager, Date end) {
        this();
        this.tournamentID = tournamentID;
        this.name = name;
        this.managerID = manager;
        this.end = end;
        this.games = new ArrayList<>();
        this.participants = new ArrayList<>();
    }
    public Tournament() {

    }

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

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> toReturn = new HashMap<>();
        toReturn.put("tournamentID", tournamentID);
        toReturn.put("name", name);
        toReturn.put("manager", managerID);
        toReturn.put("end", end);
        toReturn.put("participants", participants.toString());
        toReturn.put("games", games);
        return toReturn;
    }
    public String toString() {
        return tournamentID;
    }

}
