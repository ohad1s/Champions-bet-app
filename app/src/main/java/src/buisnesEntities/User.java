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


    public User(String userID, String email ,String password, String nickname){
        this.userID = userID;
        this.email=email;
        this.nickname=nickname;
        this.password=password;
        this.myTournaments = new ArrayList<Tournament>();

    }
    public User() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Tournament> getMyTournaments() {
        return myTournaments;
    }

    public void setMyTournaments(List<Tournament> myTournaments) {
        this.myTournaments = myTournaments;
    }


    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> toReturn = new HashMap<>();
        toReturn.put("userID", userID);
        toReturn.put("email", email);
        toReturn.put("nickname", nickname);
        toReturn.put("password", password);
        toReturn.put("myTournaments", myTournaments.toString());
        return toReturn;
    }
    public String toString() {
        return userID;
    }
}
