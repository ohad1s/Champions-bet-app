package com.example.champions.manager;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.champions.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import src.buisnesEntities.User;

public class ManagerRegisterActivity extends AppCompatActivity {

    private Button register;
    public FirebaseAuth auth;

    FirebaseFirestore firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_register);

        EditText email = findViewById(R.id.regEmailManager);
        EditText password = findViewById(R.id.regPasswordManager);
        EditText nickname = findViewById(R.id.regNicknameManager);
        register = findViewById(R.id.register_manager_2);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseFirestore.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString();
                String text_password = password.getText().toString();
                String text_nickname = nickname.getText().toString();
                if (TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password) || TextUtils.isEmpty(text_nickname)) {
                    Toast.makeText(ManagerRegisterActivity.this, "Empty credentials", Toast.LENGTH_SHORT).show();
                } else if (text_password.length() < 6) {
                    Toast.makeText(ManagerRegisterActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(text_email, text_password, text_nickname);
//                    Intent toManagerLogin = new Intent(ManagerRegisterActivity.this, MangerLoginActivity.class);
//                    startActivity(toManagerLogin);
//                    finish();
                }
            }
        });

    }


    private void registerUser(String text_email, String text_password, String text_nickname) {
        auth.createUserWithEmailAndPassword(text_email, text_password).addOnCompleteListener(   this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = auth.getCurrentUser();
                    String userid =  auth.getUid();
                    Log.d(TAG, auth.getUid());
                    addUserToDB(userid, text_email, text_password, text_nickname);
                    Toast.makeText(ManagerRegisterActivity.this, "Register successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManagerRegisterActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    public void addUserToDB(String userid, String email, String password, String nickname) {
        User user = new User(userid, email, password, nickname);
//        firebaseDatabase.collection("users").document(user.getUserID()).set(user.toHashMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
        firebaseDatabase.collection("users").document(user.getUserID()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ManagerRegisterActivity.this, "add succees", Toast.LENGTH_SHORT).show();
                    //send userID
                    Intent intent = new Intent(ManagerRegisterActivity.this, ManagerMainActivity.class);
                    Bundle b = new Bundle();
                    b.putString("userid", userid); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
//                    finish();
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}