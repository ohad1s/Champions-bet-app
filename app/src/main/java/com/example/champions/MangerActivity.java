package com.example.champions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MangerActivity extends AppCompatActivity {
    private Button login;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger);
    login = findViewById(R.id.login_manager);
    register =findViewById(R.id.register_manager);
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MangerActivity.this, LoginActivityManger.class));
            finish();
        }
    });
    register.setOnClickListener((new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MangerActivity.this, RegisterActivityManger.class));
            finish();
        }
    }));

    }

}