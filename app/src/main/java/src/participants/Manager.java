package src.participants;

import java.util.ArrayList;

import src.games.Group;

public class Manager extends User {
    ArrayList<Group> my_groups;

    protected Manager(String email, String password, String nickname) {
        super(email, password, nickname);
        this.my_groups= new ArrayList<Group>();

    }
}
