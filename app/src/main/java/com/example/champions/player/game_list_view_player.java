package com.example.champions.player;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.champions.DAL.GameBaseAdapter;
import com.example.champions.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import src.buisnesEntities.Game;
import src.buisnesEntities.Tournament;
import src.buisnesEntities.User;

public class game_list_view_player extends AppCompatActivity {
    protected User user; // the user is connected
    protected Tournament tournament; // the user is connected
    protected int tournamentIndex;
    private FirebaseFirestore firebaseDatabase; // the data base we work on
    int TournamentImg[] = {R.drawable.cuppp}; // img tournament
    ListView listView; // list view of tournament
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list_view);
        firebaseDatabase = FirebaseFirestore.getInstance();
        Bundle b = getIntent().getExtras();
        String tourId = "hello"; // or other values
        String userId = "hello"; // or other values
        if (b != null) {
            tourId = b.getString("tournamentid");
            userId = b.getString("userid");
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
                afteronCreate();
            }
        });
    }
    public void afteronCreate() {
        List<Game> tournament_games = tournament.getGames();
        listView = (ListView) findViewById(R.id.MyGamesListView);
        GameBaseAdapter tournamentGamesBaseAdapter = new GameBaseAdapter(getApplicationContext(), tournament_games, TournamentImg);
        listView.setAdapter(tournamentGamesBaseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("COSTUM_LIST_VIEW", "Item is clicked @ i :: " + i );
                Intent intent = new Intent(game_list_view_player.this, tournament_user_page.class);
                Bundle b = new Bundle();
                b.putString("token",tournament.getTournamentID());
                b.putString("userid", user.getUserID());
                b.putString("tournamentid", tournament.getTournamentID()); //tournament id
                b.putString("gameId", tournament_games.get(i).getGameID());
                b.putInt("gameIndex", i);
                b.putInt("tournamentIndex", tournamentIndex);
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
//                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
