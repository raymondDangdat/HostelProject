package com.example.raymond.hostel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private TextView emailDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null){
            // the user is login
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        // if the user is logged in
        FirebaseUser user = firebaseAuth.getCurrentUser();

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        emailDisplay = (TextView) findViewById(R.id.emailDisplay);
        emailDisplay.setText("Welcome " + user.getEmail());
        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLogout){
            firebaseAuth.signOut();

            finish();
            startActivity(new Intent(this, LoginActivity.class));

        }


    }
}
