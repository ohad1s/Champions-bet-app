package com.example.champions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import src.games.Game;
import src.games.Tournament;
import src.games.User;

public class tournament_user_page extends AppCompatActivity {
    private FirebaseFirestore firebaseDatabase;
    private String userId;
    User user;
    Tournament tournament;
    String token;
    ListView listView;
    int[] ballImg = {R.drawable.ball_icon};
    TextView tv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tor_page);
        firebaseDatabase = FirebaseFirestore.getInstance();
        Bundle b = getIntent().getExtras();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
// Get the user ID
        assert currentUser != null;
        userId = currentUser.getUid();
        token = b.getString("token");
        DocumentReference docRef = firebaseDatabase.collection("tournaments").document(token);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                tournament = documentSnapshot.toObject(Tournament.class);
                tv = findViewById(R.id.tour_name);
                tv.setText(tournament.getName());
                afterOnCreate();
            }
        });
    }

    private void afterOnCreate() {

        List<Game> tourBets = tournament.getGames();
        listView = (ListView) findViewById(R.id.my_gameslistview);
        BetsPlayerBaseAdapter betsAdapter = new BetsPlayerBaseAdapter(getApplicationContext(), tourBets, ballImg);
        listView.setAdapter(betsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("clicked!");
                Log.i("COSTUM_LIST_VIEW", "Item is clicked @ i :: " + i );
                Intent intent = new Intent(tournament_user_page.this, activity_game_player.class);
                Bundle b = new Bundle();
                b.putString("userid", userId);
                b.putString("tournamentid", tournament.getTournamentID()); //tournament id
                b.putInt("gameIndex", i);
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
                finish();
            }
        });
    }
}



