package com.example.champions;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.champions.manager.MangerLoginActivity;
import com.example.champions.player.PlayerLoginActivity;

/**
 * This java class charge the main activity and to chose how you want to enter the app
 * as a manager/user
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ImageView logoImage= (ImageView) findViewById(R.id.logo);
//        logoImage.setImageResource(R.drawable.logo_champions);
    }
    /**
     * When you click this bottom you pass to the manager main activity
     * @param view the view we work on
     */
    public void onClickManager(View view){
        startActivity(new Intent(MainActivity.this, MangerLoginActivity.class));
//        finish();
    }
    /**
     * When you click this bottom you pass to the player main activity
     * @param view the view we work on
     */
    public void onClickPlayer(View view){
        startActivity(new Intent(MainActivity.this, PlayerLoginActivity.class));
//        finish();
    }
}