package com.example.champions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kotlin.jvm.internal.Ref;
import src.games.Bet;
import src.games.Game;
import src.games.Team;
import src.games.User;

public class ManagerRegisterActivity extends AppCompatActivity {

    private Button register;
    public FirebaseAuth auth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_register);

        EditText email = findViewById(R.id.regEmailManager);
        EditText password = findViewById(R.id.regPasswordManager);
        register = findViewById(R.id.register_manager_2);
        auth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance("https://champions-f121f-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString();
                String text_password = password.getText().toString();
                if (TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password)) {
                    Toast.makeText(ManagerRegisterActivity.this, "Empty credentials", Toast.LENGTH_SHORT).show();
                } else if (text_password.length() < 6) {
                    Toast.makeText(ManagerRegisterActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(text_email, text_password);
                    addUserToDB(text_email, text_password, "itamar");
                    Intent toManagerLogin = new Intent(ManagerRegisterActivity.this, MangerLoginActivity.class);
                    startActivity(toManagerLogin);
                    finish();

//                    User user = new User(text_email, text_password, "nickname");
//                    firebaseDatabase.getReference().child("users").child("managers").updateChildren(user.toHashMap());

//            System.out.println(text_email + ", " + text_password);
                }
            }
        });

    }


    private void registerUser(String text_email, String text_password) {
        auth.createUserWithEmailAndPassword(text_email, text_password).addOnCompleteListener(   this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ManagerRegisterActivity.this, "Register successful!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ManagerRegisterActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void addUserToDB(String email, String password, String nickname) {
        User user = new User(email, password, nickname);
        Date date = new Date(System.currentTimeMillis());
        Team team = new Team();
        Game game = new Game(team, team, date);
        Bet bet = new Bet(user, 0, 0, game);
        ArrayList<Bet> list = new ArrayList<Bet>();
        list.add(bet);
        user.setUserBets(list);
        firebaseDatabase.getReference().child("users").updateChildren(user.toHashMap());
//        Map temp = new HashMap<String, String>();
//        temp.put("Itamar", "King");
//        firebaseDatabase.getReference("Itamar/Itamar/Dvir").child("Dvir Gev").updateChildren(temp);
    }
}