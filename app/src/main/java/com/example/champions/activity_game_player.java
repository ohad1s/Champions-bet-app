package com.example.champions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import src.games.Bet;
import src.games.Game;
import src.games.Tournament;
import src.games.User;

public class activity_game_player extends AppCompatActivity {
    protected User user; // the user is connected
    protected Tournament tournament; // the tournament is connected
    protected Game game; // the game is connected
    protected int gameIndex;
    protected int tournamentIndex;
    private FirebaseFirestore firebaseDatabase; // the data base we work on
    private String game_name;

    private EditText home_score_EditText;
    private TextView game_name_tv;
    private EditText away_score_EditText;
    private Button save_game_Button;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_player);
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
        System.out.println(tourId);
        DocumentReference docRef = firebaseDatabase.collection("users").document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                System.out.println("first!!!");
            }
        });
        docRef = firebaseDatabase.collection("tournaments").document(tourId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                System.out.println("second!!");
                tournament = documentSnapshot.toObject(Tournament.class);
                assert tournament != null;
                System.out.println(gameIndex);
                System.out.println(tournament.getGames());
                game = tournament.getGames().get(gameIndex);
                System.out.println(game);
                if (game == null) {
                    System.out.println("ZZZZZZZZZZZZZZ\n\n\nZZZZZZZZZZZZZZZZ");
                } else {
                    System.out.println("NO NULL");
                }
                game_name=game.getName();
                afteronCreate();
            }
        });
    }

    public void afteronCreate() {
        home_score_EditText = (EditText) findViewById(R.id.home_score);
        game_name_tv = (TextView) findViewById(R.id.game_name);
        away_score_EditText = (EditText) findViewById(R.id.away_score);
        save_game_Button = (Button) findViewById(R.id.save_game);
        if (game==null){
            System.out.println("null game");
        }
        else{
            game_name_tv.setText(game_name);
        }

    }

    public void SaveGame(View view) throws ParseException {
        Date currentDate = new Date();
        if (currentDate.after(game.getFinal_date())) {
            Toast.makeText(activity_game_player.this, "Game already started!", Toast.LENGTH_SHORT).show();
        } else {

            int homeScore = Integer.parseInt(home_score_EditText.getText().toString());
            int awayScore = Integer.parseInt(away_score_EditText.getText().toString());
            Bet bet = new Bet(user.getUserID(), homeScore, awayScore, game.getGameID());
            tournament.getGames().get(gameIndex).getBets().add(bet);

            firebaseDatabase.collection("tournaments").document(tournament.getTournamentID()).set(tournament).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(activity_game_player.this, "Saved!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
