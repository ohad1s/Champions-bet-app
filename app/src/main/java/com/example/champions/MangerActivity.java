package com.example.champions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MangerActivity extends AppCompatActivity {
    private Button login;
    public FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger);
        EditText email = findViewById(R.id.userNameManager);
        EditText password = findViewById(R.id.passwordManager);
        login = findViewById(R.id.login_manager);
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
        auth.signInWithEmailAndPassword(text_email,text_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MangerActivity.this,"Login successful!", Toast.LENGTH_SHORT ).show();
                startActivity(new Intent(MangerActivity.this, managerMainPage.class));
                finish();
            }
        });
    }

    public void onClickManagerRegister(View view){
        startActivity(new Intent(MangerActivity.this, RegisterActivityManger.class));
        finish();
    }

}