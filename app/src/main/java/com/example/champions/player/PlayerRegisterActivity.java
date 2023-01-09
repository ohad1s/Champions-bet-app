package com.example.champions.player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.champions.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import src.buisnesEntities.User;

public class PlayerRegisterActivity extends AppCompatActivity {
    private Button register;
    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_register);
        EditText email = findViewById(R.id.regEmailPlayer);
        EditText password = findViewById(R.id.regPasswordPlayer);
        EditText nickName = findViewById(R.id.regNicknamePlayer);
        register = findViewById(R.id.register_player_2);
        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString();
                String text_password = password.getText().toString();
                String text_nickname = nickName.getText().toString();
                if (TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password)) {
                    Toast.makeText(PlayerRegisterActivity.this, "Empty credentials", Toast.LENGTH_SHORT).show();
                } else if (text_password.length() < 6) {
                    Toast.makeText(PlayerRegisterActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(text_email, text_password, text_nickname);
//            System.out.println(text_email + ", " + text_password);
                }
            }
        });
    }

    private void registerUser(String text_email, String text_password, String text_nickname) {
        auth.createUserWithEmailAndPassword(text_email, text_password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(PlayerRegisterActivity.this, "Register successful!", Toast.LENGTH_SHORT).show();
                    addUserToDB(auth.getUid(), text_email, text_password, text_nickname);
                    startActivity(new Intent(PlayerRegisterActivity.this, PlayerMainActivity.class));
                } else {
                    Toast.makeText(PlayerRegisterActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void addUserToDB(String userid, String email, String password, String nickname) {
        User user = new User(userid, email, password, nickname);
        FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
        firebaseDatabase.collection("users").document(user.getUserID()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(PlayerRegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                    //send userID
//                    Intent intent = new Intent(PlayerRegisterActivity.this, PlayerMainActivity.class);
//                    Bundle b = new Bundle();
//                    b.putString("userid", userid); //Your id
//                    intent.putExtras(b); //Put your id to your next Intent
//                    startActivity(intent);
//                    finish();
                }
            }
        });

    }
    public static class CreateNewTournament extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_new_tournament);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}