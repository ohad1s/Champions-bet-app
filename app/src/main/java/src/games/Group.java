package src.games;
import java.util.ArrayList;
import src.participants.Manager;
import src.participants.User;

public class Group {
    private ArrayList<User> users ;
    private String code;
    private Manager manager;

    public Group(String code,Manager manager){
        this.code=code;
        this.manager=manager;
        this.users= new ArrayList<User>();
        users.add(manager);
    }
}
