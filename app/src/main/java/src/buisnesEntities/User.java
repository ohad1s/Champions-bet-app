package src.buisnesEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    private String userID;
    private String email;
    private String password;
    private String nickname;
    private List<Tournament> myTournaments;
    private int updates;

    //empty constructor
    public User() {
    }

    /**
     * constructor
     *
     * @param userID
     * @param email
     * @param password
     * @param nickname
     */
    public User(String userID, String email, String password, String nickname) {
        this.userID = userID;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.myTournaments = new ArrayList<Tournament>();
        this.updates = 0;
    }

    //getter for updates
    public int getUpdates() {
        return updates;
    }

    //getter for userID
    public String getUserID() {
        return userID;
    }

    //setter for userID
    public void setUserID(String userID) {
        this.userID = userID;
    }

    //getter for email
    public String getEmail() {
        return email;
    }

    //setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    //getter for password
    public String getPassword() {
        return password;
    }

    //setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    //getter for nickname
    public String getNickname() {
        return nickname;
    }

    //setter for nickname
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    //getter for myTournaments
    public List<Tournament> getMyTournaments() {
        return myTournaments;
    }

    //setter for myTournaments
    public void setMyTournaments(List<Tournament> myTournaments) {
        this.myTournaments = myTournaments;
    }

    //increment updates by 1
    public void update() {
        this.updates += 1;
    }

    //clear updates
    public void clearUpdates() {
        this.updates = 0;
    }

    //convert object to HashMap
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> toReturn = new HashMap<>();
        toReturn.put("userID", userID);
        toReturn.put("email", email);
        toReturn.put("nickname", nickname);
        toReturn.put("password", password);
        toReturn.put("myTournaments", myTournaments.toString());
        toReturn.put("updates", updates);
        return toReturn;
    }

    //overriding toString method
    public String toString() {
        return userID;
    }
}
