package src.participants;

import java.util.ArrayList;

import src.games.Group;

public class Player extends User {
    ArrayList<Group> my_groups;

    protected Player(String email, String password, String nickname) {
        super(email, password, nickname);
        this.my_groups = new ArrayList<Group>();
    }

    public boolean join_group(String code, Group g){
        if (code!=g.getCode()){
            return false;
        }
        else{
            my_groups.add(g);
            g.add_user(this,code);
            return true;
        }
    }
}
