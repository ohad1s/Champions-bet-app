package com.example.champions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class PlayerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_main);

        ImageView imageView = (ImageView) findViewById(R.id.tournament1);
        //new DownloadImageFromInternet((ImageView) findViewById(R.id.image_view)).execute("https://pbs.twimg.com/profile_images/630285593268752384/iD1MkFQ0.png");
    }

}