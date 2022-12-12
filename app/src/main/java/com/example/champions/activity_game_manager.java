package com.example.champions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import src.games.Tournament;
import src.games.User;

public class activity_game_manager extends AppCompatActivity {
    protected User user; // the user is connected
    protected Tournament tournament; // the tournament is connected
    protected Game game; // the game is connected
    protected int gameIndex;
    protected int tournamentIndex;
    private FirebaseFirestore firebaseDatabase; // the data base we work on

    private TextView game_name_TextView;
    private EditText game_date_EditText;
    private TextView home_name_TextView;
    private EditText home_score_EditText;
    private TextView away_name_TextView;
    private EditText away_score_EditText;
    private Button save_game_Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_manager);
        firebaseDatabase = FirebaseFirestore.getInstance();
        Bundle b = getIntent().getExtras();
        String userId = "hello"; // or other values
        String tourId = "hello"; // or other values
        String gameId = "hello"; // or other values
        if (b != null) {
            tourId = b.getString("tournamentid");
            userId = b.getString("userid");
            gameId = b.getString("gameId");
            gameIndex = b.getInt("gameIndex");
            tournamentIndex = b.getInt("tournamentIndex");
        }
        DocumentReference docRef = firebaseDatabase.collection("users").document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
            }
        });
        docRef = firebaseDatabase.collection("tournaments").document(tourId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                tournament = documentSnapshot.toObject(Tournament.class);
            }
        });
        docRef = firebaseDatabase.collection("games").document(gameId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                game = documentSnapshot.toObject(Game.class);
                afteronCreate();
            }
        });
    }
    public void afteronCreate() {
        game_name_TextView = (TextView) findViewById(R.id.game_name);
        game_date_EditText = (EditText) findViewById(R.id.game_date);
        home_name_TextView = (TextView) findViewById(R.id.home_name);
        home_score_EditText = (EditText) findViewById(R.id.home_score);
        away_name_TextView = (TextView) findViewById(R.id.away_name);
        away_score_EditText = (EditText) findViewById(R.id.away_score);
        save_game_Button = (Button) findViewById(R.id.save_game);

        game_name_TextView.setText(game.getGameID());
        try {
            game_date_EditText.setText(new SimpleDateFormat("dd/MM/yyyy").parse(game.getFinal_date().toString()).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        home_name_TextView.setText(game.getHome().getName());
        home_score_EditText.setText(game.getHome_score() + "");
        away_name_TextView.setText(game.getAway().getName());
        away_score_EditText.setText(game.getAway_score() + "");
    }

    public void SaveGame(View view) throws ParseException {
        Date gameDate =  new SimpleDateFormat("dd/MM/yyyy").parse(game_date_EditText.getText().toString());
        int homeScore = Integer.parseInt(home_score_EditText.getText().toString());
        int awayScore = Integer.parseInt(away_score_EditText.getText().toString());
        game.setFinal_date(gameDate);
        game.setHome_score(homeScore);
        game.setAway_score(awayScore);


        tournament.getGames().set(gameIndex, game);
        user.getMyTournaments().set(tournamentIndex, tournament);

        firebaseDatabase.collection("games").document(game.getGameID()).set(game).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity_game_manager.this, "add succees", Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseDatabase.collection("tournaments").document(tournament.getTournamentID()).set(tournament).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity_game_manager.this, "add succees", Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseDatabase.collection("users").document(user.getUserID()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(activity_game_manager.this, games_list_view.class);
                    Bundle b = new Bundle();
                    b.putString("userid", user.getUserID());
                    b.putString("tournamentid", tournament.getTournamentID()); //tournament id
                    b.putInt("tournamentIndex", tournamentIndex);
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}