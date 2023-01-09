package com.example.champions.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.champions.R;
import com.example.champions.player.activity_game_player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import src.buisnesEntities.Bet;
import src.buisnesEntities.Game;
import src.buisnesEntities.Tournament;
import src.buisnesEntities.User;

public class activity_game_manager extends AppCompatActivity {
    protected User user; // the user is connected
    protected Tournament tournament; // the tournament is connected
    protected Game game; // the game is connected
    protected int gameIndex;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    protected int tournamentIndex;
    private FirebaseFirestore firebaseDatabase; // the data base we work on

    private TextView game_name_TextView;
    private EditText home_score_EditText;
    private EditText away_score_EditText;
    private Button Done_Button;
    private TextView game_name_TextView_2;
    private TextView score_up_tv;
    private String userId = "hello"; // or other values
    private String tourId = "hello"; // or other values
    private String gameId = "hello"; // or other values

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_manager);
        firebaseDatabase = FirebaseFirestore.getInstance();
        initDatePicker();
        dateButton = findViewById(R.id.finalDate);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            tourId = b.getString("tournamentid");
            userId = b.getString("userid");
            gameId = b.getString("gameId");
            gameIndex = b.getInt("gameIndex");
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
                game = tournament.getGames().get(gameIndex);
                afteronCreate();
            }
        });
//        docRef = firebaseDatabase.collection("games").document(gameId);
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                game = documentSnapshot.toObject(Game.class);
//                afteronCreate();
//            }
//        });
    }

    public void afteronCreate() {
        game_name_TextView = (TextView) findViewById(R.id.game_name);
        home_score_EditText = (EditText) findViewById(R.id.home_score);
        away_score_EditText = (EditText) findViewById(R.id.away_score);
        Done_Button = (Button) findViewById(R.id.save_game);
        game_name_TextView_2 = (TextView) findViewById(R.id.GameEditName);
        game_name_TextView.setText(game.getName());
        game_name_TextView_2.setText(game.getName());
        score_up_tv = (TextView) findViewById(R.id.last_score_null);
        DocumentReference docRef = firebaseDatabase.collection("tournaments").document(tourId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                tournament = documentSnapshot.toObject(Tournament.class);
                game = tournament.getGames().get(gameIndex);
                int h = game.getHome_score();
                int a = game.getAway_score();
                String last_score = h + " " +"-" + " " + a;
                score_up_tv.setText(last_score);
            }
        });
    }

    public void SaveGame(View view) throws ParseException {
        Date currentDate = new Date();
        if (currentDate.before(game.getFinal_date())) {
            Toast.makeText(activity_game_manager.this, "Game not yet played!", Toast.LENGTH_SHORT).show();
        } else {

            int homeScore = Integer.parseInt(home_score_EditText.getText().toString());
            int awayScore = Integer.parseInt(away_score_EditText.getText().toString());
            game.setHome_score(homeScore);
            game.setAway_score(awayScore);
            tournament.getGames().set(gameIndex, game);

            scoreUpdate(homeScore, awayScore);

            user.getMyTournaments().set(tournamentIndex, tournament);

            firebaseDatabase.collection("games").document(game.getGameID()).set(game).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(activity_game_manager.this, "add succees", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            firebaseDatabase.collection("tournaments").document(tournament.getTournamentID()).set(tournament).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(activity_game_manager.this, "add succees", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            firebaseDatabase.collection("users").document(user.getUserID()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
//                    Intent intent = new Intent(activity_game_manager.this, games_list_view.class);
//                    Bundle b = new Bundle();
//                    b.putString("userid", user.getUserID());
//                    b.putString("tournamentid", tournament.getTournamentID()); //tournament id
//                    b.putInt("tournamentIndex", tournamentIndex);
//                    intent.putExtras(b); //Put your id to your next Intent
//                    startActivity(intent);
//                    finish();
                        onBackPressed();
                    }
                }
            });
        }
    }

    public void DeleteMistake(View view) throws ParseException {
        Date currentDate = new Date();
        if (currentDate.before(game.getFinal_date())) {
            Toast.makeText(activity_game_manager.this, "Game not yet played!", Toast.LENGTH_SHORT).show();
        } else {
        int homeScore = Integer.parseInt(home_score_EditText.getText().toString());
        int awayScore = Integer.parseInt(away_score_EditText.getText().toString());
        game.setHome_score(homeScore);
        game.setAway_score(awayScore);
        tournament.getGames().set(gameIndex, game);

        scoreMistake(homeScore, awayScore);

        user.getMyTournaments().set(tournamentIndex, tournament);

        firebaseDatabase.collection("games").document(game.getGameID()).set(game).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity_game_manager.this, "add succees", Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseDatabase.collection("tournaments").document(tournament.getTournamentID()).set(tournament).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity_game_manager.this, "add succees", Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseDatabase.collection("users").document(user.getUserID()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
//                    Intent intent = new Intent(activity_game_manager.this, games_list_view.class);
//                    Bundle b = new Bundle();
//                    b.putString("userid", user.getUserID());
//                    b.putString("tournamentid", tournament.getTournamentID()); //tournament id
//                    b.putInt("tournamentIndex", tournamentIndex);
//                    intent.putExtras(b); //Put your id to your next Intent
//                    startActivity(intent);
//                    finish();
                    onBackPressed();
                }
            }
        });
    }
    }

    private void scoreUpdate(int home_score, int away_score) {
        ArrayList<Bet> bets = game.getBets();
        for (Bet b : bets) {
            String userID = b.getBetID();
            int h_score = b.getHome_score();
            int a_score = b.getAway_score();
            int score_to_add = 0;
            if (h_score == home_score && a_score == away_score) {
                score_to_add = 3;
            } else {
                if ((h_score - a_score) == (home_score - away_score)) {
                    score_to_add = 1;
                }
            }
            tournament.getLeaderboard().updateUserScore(userID, score_to_add);
        }
    }

    private void scoreMistake(int home_score, int away_score) {
        ArrayList<Bet> bets = game.getBets();
        for (Bet b : bets) {
            String userID = b.getBetID();
            int h_score = b.getHome_score();
            int a_score = b.getAway_score();
            int score_to_add = 0;
            if (h_score == home_score && a_score == away_score) {
                score_to_add = -3;
            } else {
                if ((h_score - a_score) == (home_score - away_score)) {
                    score_to_add = -1;
                }
            }
            tournament.getLeaderboard().updateUserScore(userID, score_to_add);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private Date String_to_Date(String d) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int spaceIndex = dateButton.getText().toString().indexOf(" ");
        String month_to_format = getMonthNumber(dateButton.getText().toString().substring(0, spaceIndex));
        String day_to_format = dateButton.getText().toString().substring(spaceIndex + 1, spaceIndex + 3);
        String year_to_format = dateButton.getText().toString().substring(spaceIndex + 4);
        String date_to_format = year_to_format + "-" + month_to_format + "-" + day_to_format;
        System.out.println(day_to_format);
        Date f_date = formatter.parse(date_to_format);
        return f_date;
    }

    private String getMonthNumber(String month) {
        switch (month) {
            case "January":
                return ("01");
            case "February":
                return ("02");
            case "March":
                return ("03");
            case "April":
                return ("04");
            case "May":
                return ("05");
            case "June":
                return ("06");
            case "July":
                return ("07");
            case "August":
                return ("08");
            case "September":
                return ("09");
            case "October":
                return ("10");
            case "November":
                return ("11");
            case "December":
                return ("12");
        }
        return "01";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
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
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

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


    public void update_final_date(View view) throws ParseException {
        Date f_date = String_to_Date(dateButton.getText().toString());
        tournament.getGames().get(gameIndex).setFinal_date(f_date);
        firebaseDatabase.collection("tournaments").document(tournament.getTournamentID()).set(tournament).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity_game_manager.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}