package src.games;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String password;
    private String nickname;

    private List<Tournament> myTournaments;

    public User(String email ,String password, String nickname){
        this.email=email;
        this.nickname=nickname;
        this.password=password;
        this.myTournaments = new ArrayList<Tournament>();
    }

}
