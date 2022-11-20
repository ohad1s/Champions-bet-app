package src.participants;

public class User {
    private String email;
    private String password;
    private String nickname;

    protected User(String email ,String password, String nickname){
        this.email=email;
        this.nickname=nickname;
        this.password=password;
    }

}

