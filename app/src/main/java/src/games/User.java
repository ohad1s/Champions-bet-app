package src.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    private String email;
    private String password;
    private String nickname;
    private List<Tournament> myTournaments;
    private List<Bet> userBets;

    public User(String email ,String password, String nickname){
        this.email=email;
        this.nickname=nickname;
        this.password=password;
        this.myTournaments = new ArrayList<Tournament>();
        this.userBets = new ArrayList<Bet>();
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

//    public List<Tournament> getMyTournaments() {
//        return myTournaments;
//    }

    public void setMyTournaments(List<Tournament> myTournaments) {
        this.myTournaments = myTournaments;
    }

//    public List<Bet> getUserBets() {
//        return userBets;
//    }

    public void setUserBets(List<Bet> userBets) {
        this.userBets = userBets;
    }
}
