package com.example.champions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import src.games.Game;
import src.games.Team;
import src.games.Tournament;
import src.games.User;

public class CreateNewTournament extends AppCompatActivity {
    private FirebaseFirestore firebaseDatabase;
    protected User user;
    private Tournament tournament;

    private EditText tournamentName;
    private EditText homeTeam;
    private EditText wayTeam;
    //    private EditText finalDate;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_tournament);

        tournamentName = findViewById(R.id.tournamentName);
        homeTeam = findViewById(R.id.homeTeam);
        wayTeam = findViewById(R.id.wayTeam);
//        finalDate = findViewById(R.id.finalDate);
        initDatePicker();
        dateButton = findViewById(R.id.finalDate);
        dateButton.setText(getTodaysDate());

        firebaseDatabase = FirebaseFirestore.getInstance();
        Bundle b = getIntent().getExtras();
        String userId = "hello"; // or other values
        if (b != null)
            userId = b.getString("userid");
        DocumentReference docRef = firebaseDatabase.collection("users").document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                afterOnCreate();
            }
        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        int month= cal.get(Calendar.MONTH);
        month+=1;
        int day= cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String d = makeDateString(day, month, year);
                dateButton.setText(d);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        int month= cal.get(Calendar.MONTH);
        int day= cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        switch (month) {
            case 1:
                return ("January");
            case 2:
                return ("February");
            case 3:
                return ("March");
            case 4:
                return ("April");
            case 5:
                return ("May");
            case 6:
                return ("June");
            case 7:
                return ("July");
            case 8:
                return ("August");
            case 9:
                return ("September");
            case 10:
                return ("October");
            case 11:
                return ("November");
            case 12:
                return ("December");
        }
        return "January";
    }

    public void afterOnCreate() {
        String tournamentID = user.getUserID() + "/" + user.getMyTournaments().size();
        tournament = new Tournament(tournamentID, "name", user.getUserID(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
    }

    public void onClickAddGameButton(View view) throws ParseException {
        String tournamentName_text = tournamentName.getText().toString();
        String homeTeam_text = homeTeam.getText().toString();
        String wayTeam_text = wayTeam.getText().toString();
//        Date finalDate_date = new SimpleDateFormat("dd/MM/yyyy").parse(finalDate.getText().toString());

        Team home = new Team(homeTeam_text, homeTeam_text);
        Team way = new Team(wayTeam_text, wayTeam_text);

        String gameID = home.getName() + " vs " + way.getName();
        Date f_date = new SimpleDateFormat("dd/MM/yyyy").parse(dateButton.getText().toString());
        Game newGame = new Game(gameID, home, way, f_date );

        tournament.setName(tournamentName_text);
        tournament.getGames().add(newGame);
        tournament.setTournamentID(tournament.getName());
        addToDB(home, way, newGame);

    }

    public void onClickDoneButton(View view) throws ParseException {
        user.getMyTournaments().add(tournament);
        firebaseDatabase.collection("tournaments").document(tournament.getTournamentID()).set(tournament).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateNewTournament.this, "add succees", Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseDatabase.collection("users").document(user.getUserID()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateNewTournament.this, "add succees", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateNewTournament.this, ManagerMainActivity.class);
                    Bundle b = new Bundle();
                    b.putString("userid", user.getUserID()); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void addToDB(Team home, Team way, Game game) {
        firebaseDatabase.collection("teams").document(home.getTeamID()).set(home).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateNewTournament.this, "add succees", Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseDatabase.collection("teams").document(way.getTeamID()).set(way).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateNewTournament.this, "add succees", Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseDatabase.collection("games").document(game.getGameID()).set(game).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateNewTournament.this, "add succees", Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseDatabase.collection("tournaments").document(tournament.getTournamentID()).set(tournament).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateNewTournament.this, "add succees", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}