package com.example.champions.DAL;
import com.example.champions.R;
import com.example.champions.player.game_list_view_player;
import com.example.champions.player.tournament_user_page;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import src.buisnesEntities.Game;
import src.buisnesEntities.Leaderboard;
import src.buisnesEntities.Tournament;

public class ActivityLeaderboard extends AppCompatActivity {
    protected Tournament tournament;
    private FirebaseFirestore firebaseDatabase; // the data base we work on
    ListView listView; // list view of tournament
    TextView title_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        firebaseDatabase = FirebaseFirestore.getInstance();
        Bundle b = getIntent().getExtras();
        String tourId = "hello"; // or other values
        if (b != null) {
            tourId = b.getString("tournamentid");
        }

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
        title_tv= (TextView) findViewById(R.id.title11);
        title_tv.setText(tournament.getName());
        Leaderboard leaderboard_table = tournament.getLeaderboard();
        Map<String, Integer> table = leaderboard_table.getSortedTable();
        listView = (ListView) findViewById(R.id.table__);
        ScoreAdapter scoreAdapter = new ScoreAdapter(getApplicationContext(), table);
        listView.setAdapter(scoreAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("token", tournament.getTournamentID());
        setResult(RESULT_OK, intent);
        finish();
    }
}