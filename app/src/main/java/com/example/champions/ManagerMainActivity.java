package com.example.champions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import src.games.Tournament;
import src.games.User;

public class ManagerMainActivity extends AppCompatActivity {
    private Button createANewTournament;
    protected User user;
    private FirebaseFirestore firebaseDatabase;
    int TournamentImg[] = {R.drawable.tournament_image};
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

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
                afteronCreate();
            }
        });
    }
    public void afteronCreate() {
        TextView textView = (TextView) findViewById(R.id.welcomeManager);
        textView.setText("Welcome " + user.getNickname() + "!");


        createANewTournament = findViewById(R.id.CreateBtn);
        createANewTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerMainActivity.this, CreateNewTournament.class);
                Bundle b = new Bundle();
                b.putString("userid", user.getUserID()); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
                finish();
            }
        });
        List<Tournament> user_tournament = user.getMyTournaments();
        listView = (ListView) findViewById(R.id.MyTournamentListView);
        TournamentMangerBaseAdapter tournamentMangerBaseAdapter = new TournamentMangerBaseAdapter(getApplicationContext(), user_tournament, TournamentImg);
        listView.setAdapter(tournamentMangerBaseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("COSTUM_LIST_VIEW", "Item is clicked @ i :: " + i );
            }
        });
    }
    public void onJoinButton(){
        startActivity(new Intent(this, JoinTorByToken.class));
    }
}