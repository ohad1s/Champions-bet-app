package src.games;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Tournament {
    private String tournamentID;
    private String name;
    private User manager;
    private Date start, end;
    private  List<Game> games;
    private  List<User> participants;

    public Tournament(String tournamentID, String name, User manager, Date start, Date end) {
        this.tournamentID = tournamentID;
        this.name = name;
        this.manager = manager;
        this.start = start;
        this.end = end;
        this.games = new ArrayList<>();
        this.participants = new ArrayList<>();
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

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
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

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> toReturn = new HashMap<>();
        toReturn.put("tournamentID", tournamentID);
        toReturn.put("name", name);
        toReturn.put("manager", manager.getUserID());
        toReturn.put("start", start);
        toReturn.put("end", end);
        toReturn.put("participants", participants.toString());
        toReturn.put("games", games);
        return toReturn;
    }
    public String toString() {
        return tournamentID;
    }
}
