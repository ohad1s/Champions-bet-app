package com.example.champions;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import src.games.Tournament;
import src.games.User;

public class JoinTorByToken extends AppCompatActivity {
    private FirebaseFirestore firebaseDatabase;
    private EditText Token;
    private String userId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_tor_by_token);

        Token = findViewById(R.id.your_token_here);

        firebaseDatabase = FirebaseFirestore.getInstance();
        Bundle b = getIntent().getExtras();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

// Get the user ID
        assert currentUser != null;
        userId = currentUser.getUid();
    }

    public void onClickEnterButton(View view) throws ParseException {

        // Get a reference to the Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

// Get a reference to the "tournaments" node in the database
        DatabaseReference tournamentsRef = database.getReference("tournaments");

// Get the ID of the tournament to add the user to
        String tournamentId = "my-tournament-id";

// Get the ID of the user to add to the tournament
        String userId = "my-user-id";

// Add the user to the tournament's participants list
        tournamentsRef.child(tournamentId).child("participants").add(userId);

// Add the tournament to the user's list of tournaments
        tournamentsRef.child(userId).child("tournaments").add(tournamentId);


    }


}
