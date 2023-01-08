package com.example.champions.DAL;
import com.example.champions.R;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import src.buisnesEntities.Tournament;

public class ActivityLeaderboard extends AppCompatActivity {
    protected Tournament tournament;
    private FirebaseFirestore firebaseDatabase; // the data base we work on
    ListView listView; // list view of tournament

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        firebaseDatabase = FirebaseFirestore.getInstance();
        Bundle b = getIntent().getExtras();
    }
}