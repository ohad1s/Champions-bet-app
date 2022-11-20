package src.games;

import java.util.ArrayList;

import src.participants.Manager;
import src.participants.User;

public class Group {
    private String name;
    private ArrayList<User> users;
    private String code;
    private Manager manager;

    public Group(String name, String code, Manager manager) {
        this.code = code;
        this.name = name;
        this.manager = manager;
        this.users = new ArrayList<User>();
        users.add(manager);
    }

    public String getCode() {
        return code;
    }

    public void add_user(User us, String code) {
        if (code==this.getCode()){
            this.users.add(us);
        }
    }
}
