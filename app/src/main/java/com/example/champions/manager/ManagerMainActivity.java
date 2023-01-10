package com.example.champions.manager;

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

import com.example.champions.DAL.TournamentMangerBaseAdapter;
import com.example.champions.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;
import java.util.Objects;

import src.buisnesEntities.Tournament;
import src.buisnesEntities.User;
/**
 * This java class charge the home page manager
 * show the tournament you manage
 * create a new tournament
 */
public class ManagerMainActivity extends AppCompatActivity {
    private Button createANewTournament; // bottom to create a new tournament
    protected User user; // the user is connected
    private FirebaseFirestore firebaseDatabase; // the data base we work on
    int TournamentImg[] = {R.drawable.cuppp}; // img tournament
    ListView listView; // list view of tournament
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
                Log.i("COSTUM_LIST_VIEW", "Item is clicked @ i :: " + i );
                Intent intent = new Intent(ManagerMainActivity.this, tournament_page.class);
                Bundle b = new Bundle();
                b.putString("userid", user.getUserID());
                Tournament tournament= user_tournament.get(i);
                System.out.println(tournament);
                System.out.println("Ciii");
//                b.putString("tournamentid", user_tournament.get(i).getTournamentID()); //tournament id
                b.putInt("tournamentIndex", i);
                b.putString("userid", user.getUserID()); //Your id
                b.putString("tourId", tournament.getTournamentID());
                b.putString("tour_name", tournament.getName());
//                b.putSerializable("tour_obj", tournament);
                intent.putExtra("tour_obj2", tournament);
                intent.putExtras(b); //Put your id to your next Intent
                if (Objects.equals(user.getUserID(), tournament.getManagerID())){
                    startActivity(intent);
//                    finish();
                }
                else{
                    Toast.makeText(ManagerMainActivity.this, "You are not the Manager of this tournament!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}