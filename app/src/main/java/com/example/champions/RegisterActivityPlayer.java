package com.example.champions;

import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivityPlayer extends AppCompatActivity {
    private Button register;
    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_player);
        EditText email = findViewById(R.id.regEmailPlayer);
        EditText password = findViewById(R.id.regPasswordPlayer);
        register = findViewById(R.id.register_player_2);
        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString();
                String text_password = password.getText().toString();
                if (TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password)) {
                    Toast.makeText(RegisterActivityPlayer.this, "Empty credentials", Toast.LENGTH_SHORT).show();
                } else if (text_password.length() < 6) {
                    Toast.makeText(RegisterActivityPlayer.this, "Password too short", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(text_email, text_password);
//            System.out.println(text_email + ", " + text_password);
                }
            }
        });
    }

    private void registerUser(String text_email, String text_password) {
        auth.createUserWithEmailAndPassword(text_email, text_password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivityPlayer.this, "Register successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivityPlayer.this, "Register failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}