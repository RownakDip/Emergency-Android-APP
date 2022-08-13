package com.example.lifesaverric;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {
    private ImageButton EmgBtn,StandBy,Logout;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        fAuth = FirebaseAuth.getInstance();
        EmgBtn = findViewById(R.id.ImageButton);
        StandBy = findViewById(R.id.ImageButton2);
        Logout= findViewById(R.id.ImageButton4);


        Logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                fAuth.signOut();
                startActivity(new Intent(HomePage.this,Login.class));
            }
        });

        EmgBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                startActivity(new Intent(HomePage.this,MapsActivity.class));
            }
        });

        StandBy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                startActivity(new Intent(HomePage.this,ShakeDetection.class));
            }
        });

    }
}