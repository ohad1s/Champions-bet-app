package src.games;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tournament {
    private String name;
    private  String code;
    private User manager;
    private Date start, end;
    private  List<Game> games;
    private  List<User> participants;


    public Tournament() {
        games = new ArrayList<Game>();
        participants = new ArrayList<User>();
    }


}
