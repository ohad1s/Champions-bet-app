package com.example.champions.player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.champions.R;
import com.example.champions.manager.ManagerMainActivity;
import com.example.champions.manager.MangerLoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PlayerLoginActivity extends AppCompatActivity {
    private Button login;
    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_login);
        EditText email = findViewById(R.id.userNamePlayer);
        EditText password = findViewById(R.id.passwordPlayer);
        login = findViewById(R.id.login_player);
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString();
                String text_password = password.getText().toString();
//                loginUser(text_email, text_password);
                String response = null;
                try {
                    response = authenticatedViaHttp(text_email, text_password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String [] responseParts = response.split(",");
                if (Objects.equals(responseParts[0], "200")) {
                    Toast.makeText(PlayerLoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PlayerLoginActivity.this, PlayerMainActivity.class));

                } else {
                    Toast.makeText(PlayerLoginActivity.this, "Invalid Username Or Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser(String text_email, String text_password) {
        auth.signInWithEmailAndPassword(text_email, text_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(PlayerLoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PlayerLoginActivity.this, PlayerMainActivity.class));
//                finish();
            }
        });
    }

    public void onClickPlayerRegister(View view) {
        startActivity(new Intent(PlayerLoginActivity.this, PlayerRegisterActivity.class));
//        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public String authenticatedViaHttp(String email, String password) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new PlayerLoginActivity.HttpRequestThread(email,password));
        return future.get();

    }

    public class HttpRequestThread implements Callable<String> {
        private String url;

        public HttpRequestThread(String email, String password) {
            this.url = "http://18.183.211.24:5000/?email=" + email + "&password=" + password;

        }

        public String call() throws Exception {
            URL urlObject = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
    }
}