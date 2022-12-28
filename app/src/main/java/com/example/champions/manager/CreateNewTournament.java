package com.example.champions.manager;

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

import com.example.champions.R;
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
import java.util.UUID;

import src.buisnesEntities.Tournament;
import src.buisnesEntities.User;

public class CreateNewTournament extends AppCompatActivity {
    private FirebaseFirestore firebaseDatabase;
    protected User user;
    private Tournament tournament;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

//    private EditText tournamentName;
//    private EditText homeTeam;
//    private EditText wayTeam;
    //    private EditText finalDate;
//    private DatePickerDialog datePickerDialog;
//    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_tournament);

        initDatePicker();
        dateButton = findViewById(R.id.finalDate);

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
            }
        });
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

    private Date String_to_Date(String d) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int spaceIndex = dateButton.getText().toString().indexOf(" ");
        String month_to_format= getMonthNumber(dateButton.getText().toString().substring(0,spaceIndex));
        String day_to_format= dateButton.getText().toString().substring(spaceIndex+1,spaceIndex+3);
        String year_to_format= dateButton.getText().toString().substring(spaceIndex+4);
        String date_to_format= year_to_format+"-"+month_to_format+"-"+day_to_format;
        System.out.println(day_to_format);
        Date f_date = formatter.parse(date_to_format);
        return f_date;
    }

    public void onClickDoneButton(View view) throws ParseException {
        UUID uuid = UUID.randomUUID();
        String uniqueToken = uuid.toString().replace("\\", "a");
        EditText TournamentNameBut = findViewById(R.id.tournamentName);
        String TournamentName = TournamentNameBut.getText().toString();
//        user.getMyTournaments().add(tournament);
        Date f_date= String_to_Date(dateButton.getText().toString());
        tournament= new Tournament(uniqueToken,TournamentName, user.getUserID(), f_date);
        tournament.getParticipants().add(user.getUserID());
        tournament.getLeaderboard().addNewUser(user.getUserID());
        firebaseDatabase.collection("tournaments").document(tournament.getTournamentID()).set(tournament).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateNewTournament.this, "Created successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        user.getMyTournaments().add(tournament);
        firebaseDatabase.collection("users").document(user.getUserID()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateNewTournament.this, "Created successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateNewTournament.this, tournament_page.class);
                    Bundle b = new Bundle();
                    b.putString("userid", user.getUserID()); //Your id
                    b.putString("tourId", tournament.getTournamentID());
                    b.putString("tour_name", tournament.getName());
                    b.putSerializable("tour_obj", tournament);
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
//                    finish();
                }
            }
        });
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}