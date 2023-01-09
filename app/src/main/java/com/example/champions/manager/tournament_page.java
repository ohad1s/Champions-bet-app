package com.example.champions.manager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.champions.DAL.ActivityLeaderboard;
import com.example.champions.R;
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
import java.util.List;

import src.buisnesEntities.Game;
import src.buisnesEntities.Team;
import src.buisnesEntities.Tournament;
import src.buisnesEntities.User;

public class tournament_page extends AppCompatActivity {
    private FirebaseFirestore firebaseDatabase;
    protected User user;
    private Tournament tournament;
    private EditText homeTeam;
    private TextView tour_name_text;
    private EditText wayTeam;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Bundle b;
    private int tournamentIndex;
    private ImageButton wtsp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_page);
        tour_name_text = findViewById(R.id.tour_name);
        homeTeam = findViewById(R.id.homeTeam);
        wayTeam = findViewById(R.id.wayTeam);
        wtsp = (ImageButton) findViewById(R.id.img_w);
        initDatePicker();
        dateButton = findViewById(R.id.finalDate);
//        dateButton = findViewById(R.id.finalDate);
//        dateButton.setText(getTodaysDate());
        firebaseDatabase = FirebaseFirestore.getInstance();
        b = getIntent().getExtras();
        String userId = "hello"; // or other values
        if (b != null) {
            userId = b.getString("userid");
//            tournament = (Tournament) b.getSerializable("tour_obj");
//            if (tournament == null){
//                tournament= (Tournament) getIntent().getSerializableExtra("tour_obj2");
//                System.out.println("im here:");
//                System.out.println("id: "+tournament);
//                System.out.println(tournament.getGames());
//            }
            tournamentIndex = b.getInt("tournamentIndex");
        }
        DocumentReference docRef = firebaseDatabase.collection("users").document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                tournament = user.getMyTournaments().get(tournamentIndex);
                afterOnCreate();
            }
        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month += 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
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

    public void afterOnCreate() {
        if (b != null) {
            TextView tv1 = (TextView) findViewById(R.id.tour_name);
            tv1.setText(this.b.getString("tour_name"));
            TextView tv2 = (TextView) findViewById(R.id.token);
            tv2.setText(this.b.getString("tourId"));
            // Enable text selection in the TextView.
            tv2.setTextIsSelectable(true);

            tv2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onLongClickHandler(view);
                    return true;
                }
            });

        }
    }

    public void onClickAddGameButton(View view) throws ParseException {
        String homeTeam_text = homeTeam.getText().toString();
        String wayTeam_text = wayTeam.getText().toString();
//        Date finalDate_date = new SimpleDateFormat("dd/MM/yyyy").parse(finalDate.getText().toString());

        Team home = new Team(homeTeam_text, homeTeam_text);
        Team way = new Team(wayTeam_text, wayTeam_text);

        String gameID = home.getName() + " VS " + way.getName();
//        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
//        int spaceIndex = dateButton.getText().toString().indexOf(" ");
//        int lenIndex = dateButton.getText().toString().length();
//        String date_to_format= dateButton.getText().toString().substring(0,3)+dateButton.getText().toString().substring(spaceIndex,lenIndex);
//        Date f_date = formatter.parse(date_to_format);
        Date f_date = String_to_Date(dateButton.getText().toString());
        Game newGame = new Game(gameID, home, way, f_date);
        addToDB(home, way, newGame);
        homeTeam.setText("");
        wayTeam.setText("");
        dateButton.setText("Last Date for betting");
        updateUsers();
    }

    private void updateUsers() {
        List<String> users =  tournament.getParticipants();
        for (String us : users){
            DocumentReference docRef = firebaseDatabase.collection("users").document(us);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    assert user != null;
                    user.update();
                }
            });
        }
    }

    public void addToDB(Team home, Team way, Game game) {
//        firebaseDatabase.collection("teams").document(home.getTeamID()).set(home).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(tournament_page.this, "add succees", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        firebaseDatabase.collection("teams").document(way.getTeamID()).set(way).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(tournament_page.this, "add succees", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        firebaseDatabase.collection("games").document(game.getGameID()).set(game).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(tournament_page.this, "add succees", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        tournament.getGames().add(game);
        user.getMyTournaments().get(tournamentIndex).getGames().add(game);
        System.out.println(tournament.getGames());
        System.out.println("im here");
        firebaseDatabase.collection("tournaments").document(tournament.getTournamentID()).set(tournament).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(tournament_page.this, "Success!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        firebaseDatabase.collection("users").document(user.getUserID()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(tournament_page.this, "Success!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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


    public void onLongClickHandler(View view) {
        // Check if the view is a TextView.
        if (view instanceof TextView) {
            // Get the TextView.
            TextView textView = (TextView) view;

            // Get the clipboard manager.
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            // Create a new clipboard item with the text from the TextView.
            ClipData clip = ClipData.newPlainText("text", textView.getText());

            // Set the clipboard item on the clipboard.
            clipboard.setPrimaryClip(clip);

            // Show a toast message.
            Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onClickEditGames(View view) {
        Intent intent = new Intent(tournament_page.this, games_list_view.class);
        Bundle b = new Bundle();
        b.putString("userid", user.getUserID());
        b.putString("tournamentid", tournament.getTournamentID()); //tournament id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }

    public void shareViaWhatsapp(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, tournament.getTournamentID());
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }

    public void onClickLeaderboard(View view) {
        Intent intent = new Intent(tournament_page.this, ActivityLeaderboard.class);
        Bundle b = new Bundle();
        b.putString("tournamentid", tournament.getTournamentID()); //tournament id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }
}
