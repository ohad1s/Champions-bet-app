package com.example.champions;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import src.games.Tournament;
import src.games.User;

public class JoinTorByToken extends AppCompatActivity {
    private FirebaseFirestore firebaseDatabase;
    private EditText Token;
    private String userId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_tor_by_token);

        Token= findViewById(R.id.your_token_here);

        firebaseDatabase = FirebaseFirestore.getInstance();
        Bundle b = getIntent().getExtras();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

// Get the user ID
        assert currentUser != null;
        this.userId = currentUser.getUid();
    }

//    public void onClickEnterButton(View view) throws ParseException {
//        DocumentReference docRef = firebaseDatabase.collection("tournaments").document(String.valueOf(this.Token));
//
//        docRef.collection("players").add(this.user.toHashMap());
//    }
}