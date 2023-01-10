package com.example.champions.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.champions.DAL.MyDialogFragment;
import com.example.champions.DAL.TournamentMangerBaseAdapter;
import com.example.champions.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import src.buisnesEntities.Tournament;
import src.buisnesEntities.User;

public class PlayerMainActivity extends AppCompatActivity {
    private Button JoinTorButton; // bottom to create a new tournament
    protected User user; // the user is connected
    private FirebaseFirestore firebaseDatabase; // the data base we work on
    int TournamentImg[] = {R.drawable.cuppp}; // img tournament
    ListView listView; // list view of tournament

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_main);

        firebaseDatabase = FirebaseFirestore.getInstance();
        Bundle b = getIntent().getExtras();
        // Get the current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Get the user ID
        assert currentUser != null;
        String userId = currentUser.getUid();

//        String userId; // or other values
//        if(b != null)
//            userId = b.getString("userid");

        DocumentReference docRef = firebaseDatabase.collection("users").document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                assert user != null;
                String msg = "Welcome back " + user.getNickname() + " You have " + user.getUpdates() + " new games to bet on!";
                MyDialogFragment dialogFragment = MyDialogFragment.newInstance(msg);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (user.getUpdates() > 0) {
                    dialogFragment.show(fragmentTransaction, "dialog");
                    user.clearUpdates();
                }
                firebaseDatabase.collection("users").document(userId).set(user);
                afteronCreate();
            }
        });

//        ImageView imageView = (ImageView) findViewById(R.id.tournament1);
        //new DownloadImageFromInternet((ImageView) findViewById(R.id.image_view)).execute("https://pbs.twimg.com/profile_images/630285593268752384/iD1MkFQ0.png");
    }

    public void afteronCreate() {
        TextView textView = (TextView) findViewById(R.id.welcomePlayer);
        textView.setText("Welcome " + user.getNickname() + "!");


        JoinTorButton = findViewById(R.id.JoinBtn);
        JoinTorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayerMainActivity.this, JoinTorByToken.class);
                Bundle b = new Bundle();
                b.putString("userid", user.getUserID()); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
//                finish();
            }
        });
        List<Tournament> user_tournament = user.getMyTournaments();
        listView = (ListView) findViewById(R.id.MyTournamentListView);
        TournamentMangerBaseAdapter tournamentMangerBaseAdapter = new TournamentMangerBaseAdapter(getApplicationContext(), user_tournament, TournamentImg);
        listView.setAdapter(tournamentMangerBaseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("COSTUM_LIST_VIEW", "Item is clicked @ i :: " + i);
                Intent intent = new Intent(PlayerMainActivity.this, tournament_user_page.class);
                Bundle b = new Bundle();
                b.putString("userid", user.getUserID());
                b.putString("tournamentid", user_tournament.get(i).getTournamentID()); //tournament id
                b.putInt("tournamentIndex", i);
                b.putString("token", user_tournament.get(i).getTournamentID());
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