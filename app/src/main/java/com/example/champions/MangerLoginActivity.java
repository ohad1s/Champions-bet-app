package com.example.champions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MangerLoginActivity extends AppCompatActivity {
    private Button login;
    public FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_login);
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
        String userEmail = text_email;
        auth.signInWithEmailAndPassword(text_email, text_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MangerLoginActivity.this,"Login successful!", Toast.LENGTH_SHORT ).show();
                    Intent intent = new Intent(MangerLoginActivity.this, ManagerMainActivity.class);
                    String userid =  auth.getUid();
                    Bundle b = new Bundle();
                    b.putString("userid", userid); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
//                    finish();
                }else{
                    Toast.makeText(MangerLoginActivity.this,"Invalid Username Or Password!", Toast.LENGTH_SHORT ).show();
                }
            }
        });
//        auth.signInWithEmailAndPassword(text_email,text_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//
//                Toast.makeText(MangerLoginActivity.this,"Login successful!", Toast.LENGTH_SHORT ).show();
//                startActivity(new Intent(MangerLoginActivity.this, ManagerMainActivity.class));
//                finish();
//            }
//
//
//        });
    }

    public void onClickManagerRegister(View view){
        startActivity(new Intent(MangerLoginActivity.this, ManagerRegisterActivity.class));
//        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}