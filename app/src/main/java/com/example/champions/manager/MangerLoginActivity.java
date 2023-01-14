package com.example.champions.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.champions.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MangerLoginActivity extends AppCompatActivity {
    private Button login;
    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_login);
        EditText email = findViewById(R.id.userNameManager);
        EditText password = findViewById(R.id.passwordManager);
        login = findViewById(R.id.login_manager);
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString();
                String text_password = password.getText().toString();

                String response = null;
                try {
                    response = authenticatedViaHttp(text_email, text_password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String [] responseParts = response.split(",");
                if (Objects.equals(responseParts[0], "200")) {
                    Toast.makeText(MangerLoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MangerLoginActivity.this, ManagerMainActivity.class);
                    String userid = responseParts[1];
                    Bundle b = new Bundle();
                    b.putString("userid", userid); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);

                } else {
                    Toast.makeText(MangerLoginActivity.this, "Invalid Username Or Password!", Toast.LENGTH_SHORT).show();
                }

//                    loginUser(text_email, text_password);
            }
        });

    }

    private void loginUser(String text_email, String text_password) {
        String userEmail = text_email;
        auth.signInWithEmailAndPassword(text_email, text_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MangerLoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MangerLoginActivity.this, ManagerMainActivity.class);
                    String userid = auth.getUid();
                    Bundle b = new Bundle();
                    b.putString("userid", userid); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
//                    finish();
                } else {
                    Toast.makeText(MangerLoginActivity.this, "Invalid Username Or Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        auth.signInWithEmailAndPassword(text_email,text_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//
//                Toast.makeText(MangerLoginActivity.this,"Login successful!", Toast.LENGTH_SHORT ).show();
//                startActivity(new Intent(MangerLoginActivity.this, ManagerMainActivity.class));
//                finish();
//            }
//
//
//        });
    }

    public void onClickManagerRegister(View view) {
        startActivity(new Intent(MangerLoginActivity.this, ManagerRegisterActivity.class));
//        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public String authenticatedViaHttp(String email, String password) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new HttpRequestThread(email,password));
        String response = future.get();
        return response;

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