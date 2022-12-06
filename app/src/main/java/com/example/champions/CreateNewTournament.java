package com.example.champions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import src.games.Game;
import src.games.Tournament;
import src.games.User;

public class CreateNewTournament extends AppCompatActivity {
    private FirebaseFirestore firebaseDatabase;
    protected User user;
    private Tournament tournament;

    private EditText tournamentName;
    private EditText homeTeam;
    private EditText wayTeam;
    private EditText finalDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_tournament);

        tournamentName = findViewById(R.id.tournamentName);
        homeTeam = findViewById(R.id.homeTeam);
        wayTeam = findViewById(R.id.wayTeam);
        finalDate = findViewById(R.id.finalDate);

        firebaseDatabase = FirebaseFirestore.getInstance();
        Bundle b = getIntent().getExtras();
        String userId = "hello"; // or other values
        if(b != null)
            userId = b.getString("userid");
        DocumentReference docRef = firebaseDatabase.collection("users").document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                afterOnCreate();
            }
        });
    }
    public void afterOnCreate() {
        String tournamentID = user.getUserID() + '/' + user.getMyTournaments().size();
        tournament = new Tournament(tournamentID, "name",user,new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
    }

    public void onClicAddGameButton(View view) throws ParseException {
        String tournamentName_text = tournamentName.getText().toString();
        String homeTeam_text = homeTeam.getText().toString();
        String wayTeam_text = wayTeam.getText().toString();
        Date finalDate_date = new SimpleDateFormat("dd/MM/yyyy").parse(finalDate.getText().toString());

        String gameID = tournament.getTournamentID() + "/" + tournament.getGames().size();
//        Game newGame = new Game(gameID, )
    }
}