package com.example.champions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import src.games.Game;
import src.games.Team;
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
        String tournamentID = user.getUserID() + "/" + user.getMyTournaments().size();
        tournament = new Tournament(tournamentID, "name",user.getUserID(),new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
    }

    public void onClickAddGameButton(View view) throws ParseException {
        String tournamentName_text = tournamentName.getText().toString();
        String homeTeam_text = homeTeam.getText().toString();
        String wayTeam_text = wayTeam.getText().toString();
        Date finalDate_date = new SimpleDateFormat("dd/MM/yyyy").parse(finalDate.getText().toString());

        Team home = new Team(homeTeam_text, homeTeam_text);
        Team way = new Team(wayTeam_text, wayTeam_text);

        String gameID = home.getName() + " vs " + way.getName();
        Game newGame = new Game(gameID,home, way, finalDate_date);

        tournament.setName(tournamentName_text);
        tournament.getGames().add(newGame);
        tournament.setTournamentID(tournament.getName());
        addToDB(home, way, newGame);

    }
    public void onClickDoneButton(View view) throws ParseException{
        user.getMyTournaments().add(tournament);
        firebaseDatabase.collection("tournaments").document(tournament.getTournamentID()).set(tournament).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateNewTournament.this, "add succees", Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseDatabase.collection("users").document(user.getUserID()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateNewTournament.this, "add succees", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateNewTournament.this, ManagerMainActivity.class);
                    Bundle b = new Bundle();
                    b.putString("userid", user.getUserID()); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    public void addToDB(Team home, Team way, Game game) {
        firebaseDatabase.collection("teams").document(home.getTeamID()).set(home).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateNewTournament.this, "add succees", Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseDatabase.collection("teams").document(way.getTeamID()).set(way).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateNewTournament.this, "add succees", Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseDatabase.collection("games").document(game.getGameID()).set(game).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateNewTournament.this, "add succees", Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseDatabase.collection("tournaments").document(tournament.getTournamentID()).set(tournament).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateNewTournament.this, "add succees", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}