package com.example.champions;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickManager(View view){
        startActivity(new Intent(MainActivity.this, MangerActivity.class));
        finish();
    }
    public void onClickPlayer(View view){
        startActivity(new Intent(MainActivity.this, PlayerActivity.class));
        finish();
    }


}