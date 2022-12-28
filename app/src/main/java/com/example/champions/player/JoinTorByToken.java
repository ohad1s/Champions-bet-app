package com.example.champions.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.champions.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;

import src.buisnesEntities.Tournament;
import src.buisnesEntities.User;

public class JoinTorByToken extends AppCompatActivity {
    private FirebaseFirestore firebaseDatabase;
    private EditText Token;
    private String userId;
    private User user;
    private Tournament tournament;
    Bundle b;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_tor_by_token);

        Token = findViewById(R.id.your_token_here);

        firebaseDatabase = FirebaseFirestore.getInstance();
        b = getIntent().getExtras();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
// Get the user ID
        assert currentUser != null;
        userId = currentUser.getUid();

        DocumentReference docRef = firebaseDatabase.collection("users").document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
            }
        });

    }

    public void onClickEnterButton(View view) throws ParseException {
        addToDB();
    }

    private void addToDB() {


        DocumentReference docRef2 = firebaseDatabase.collection("tournaments").document(Token.getText().toString());
        docRef2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                tournament = documentSnapshot.toObject(Tournament.class);
                if (tournament.getParticipants().contains(userId)){
                    Toast.makeText(JoinTorByToken.this, "You are already a member in this tournament!", Toast.LENGTH_SHORT).show();

                }
                else{
                    user.getMyTournaments().add(tournament);
                    tournament.getParticipants().add(userId);
                    tournament.getLeaderboard().addNewUser(userId);
                    addToDbHelper();
                    b.putString("token", Token.getText().toString());
                    Intent intent = new Intent(JoinTorByToken.this, tournament_user_page.class);
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                }
//                finish();
            }
        });
    }

    private void addToDbHelper() {
        firebaseDatabase.collection("tournaments").document(tournament.getTournamentID()).set(tournament).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(JoinTorByToken.this, "Joined successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        firebaseDatabase.collection("users").document(user.getUserID()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(JoinTorByToken.this, "Joined successfully", Toast.LENGTH_SHORT).show();
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
