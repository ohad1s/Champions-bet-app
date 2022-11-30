package com.example.champions;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import src.games.User;

public class RegisterActivityManger extends AppCompatActivity {

    private Button register;
    public FirebaseAuth auth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_manager);

        EditText email = findViewById(R.id.regEmailManager);
        EditText password = findViewById(R.id.regPasswordManager);
        register = findViewById(R.id.register_manager_2);
        auth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/User/");


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString();
                String text_password = password.getText().toString();
                if (TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password)) {
                    Toast.makeText(RegisterActivityManger.this, "Empty credentials", Toast.LENGTH_SHORT).show();
                } else if (text_password.length() < 6) {
                    Toast.makeText(RegisterActivityManger.this, "Password too short", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(text_email, text_password);
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
                    Toast.makeText(RegisterActivityManger.this, "Register successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivityManger.this, "Register failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        User user = new User(text_email, text_password, "Dviro");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(user);

                // after adding this data we are showing toast message.
                Toast.makeText(RegisterActivityManger.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(RegisterActivityManger.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference.setValue("hello");

        Toast.makeText(RegisterActivityManger.this, "I am PO!", Toast.LENGTH_SHORT).show();
    }
}