package com.example.acinstagramcone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView txtWelcome= findViewById(R.id.txtWelcome);

        //One way to get username is through Intent
        //String userName=getIntent().getStringExtra("UserName");
        //txtWelcome.setText("Welcome " + userName);

        //Another way is through getCurrentUser().get("username");

        txtWelcome.setText("Welcome"+ ParseUser.getCurrentUser().get("username"));

        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //One way to logout is through getCurrentUser
                //ParseUser.getCurrentUser().logOut();

                //Second one will log out us from application ParseUser.logOut();
                ParseUser.logOut();
                finish();
            }
        });

    }
}
