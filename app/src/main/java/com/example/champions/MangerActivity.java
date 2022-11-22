package com.example.champions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MangerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger);

    }
    public void onClickManagerLogin(View view){
        EditText userName = findViewById(R.id.userNameManager);
        EditText pass = findViewById(R.id.passwordManager);

        startActivity(new Intent(MangerActivity.this, managerMainPage.class));
        finish();
    }
    public void onClickManagerRegister(View view){
        startActivity(new Intent(MangerActivity.this, RegisterActivityManger.class));
        finish();
    }

}