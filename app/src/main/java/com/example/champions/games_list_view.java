package com.example.champions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import src.games.Game;
import src.games.Tournament;
import src.games.User;

public class games_list_view extends AppCompatActivity {
    protected Tournament tournament; // the user is connected
    private FirebaseFirestore firebaseDatabase; // the data base we work on
    int TournamentImg[] = {R.drawable.tournament_image}; // img tournament
    ListView listView; // list view of tournament
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list_view);
        firebaseDatabase = FirebaseFirestore.getInstance();
        Bundle b = getIntent().getExtras();
        String tourId = "hello"; // or other values
        if(b != null)
            tourId = b.getString("tournamentid");
        DocumentReference docRef = firebaseDatabase.collection("tournaments").document(tourId);
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
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.i("COSTUM_LIST_VIEW", "Item is clicked @ i :: " + i );
//                Intent intent = new Intent(ManagerMainActivity.this, games_list_view.class);
//                Bundle b = new Bundle();
//                b.putString("tournamentid", user_tournament.get(i).getTournamentID()); //tournament id
//                intent.putExtras(b); //Put your id to your next Intent
//                startActivity(intent);
//                finish();
//            }
//        });

    }
}