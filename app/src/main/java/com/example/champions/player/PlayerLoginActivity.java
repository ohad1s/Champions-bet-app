package com.example.champions.player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.champions.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PlayerLoginActivity extends AppCompatActivity {
    private Button login;
    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_login);
        EditText email = findViewById(R.id.userNamePlayer);
        EditText password = findViewById(R.id.passwordPlayer);
        login = findViewById(R.id.login_player);
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString();
                String text_password = password.getText().toString();
                loginUser(text_email, text_password);
            }
        });
    }

    private void loginUser(String text_email, String text_password) {
        auth.signInWithEmailAndPassword(text_email, text_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(PlayerLoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PlayerLoginActivity.this, PlayerMainActivity.class));
//                finish();
            }
        });
    }

    public void onClickPlayerRegister(View view) {
        startActivity(new Intent(PlayerLoginActivity.this, PlayerRegisterActivity.class));
//        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}