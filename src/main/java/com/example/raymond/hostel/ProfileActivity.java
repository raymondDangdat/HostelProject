package com.example.raymond.hostel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private TextView emailDisplay;

    //get the two editTexts
    private EditText editTextMatNo, editTextDepartment;
    private Button buttonSave;


    // get database reference
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
//
//        if (firebaseAuth.getCurrentUser() == null){
//            // the user is login
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Application");
        //reference the edittext
        // if the user is logged in get the current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        editTextMatNo =  findViewById(R.id.editTexMatNo);
        editTextDepartment =  findViewById(R.id.editTextDepartment);
        buttonSave =  findViewById(R.id.buttonSave);



        emailDisplay =  findViewById(R.id.emailDisplay);
        emailDisplay.setText(getString(R.string.welcome) + user.getEmail());


        buttonSave.setOnClickListener(this);
    }

    private void saveStudentInformation(){
        String matNumber = editTextMatNo.getText().toString().trim();
        String Department = editTextDepartment.getText().toString().trim();

        //get object of studentInfromation class
        StudentInformation studentInformation = new StudentInformation(matNumber, Department);

        //get currrent user of firebase
        FirebaseUser user = firebaseAuth.getCurrentUser();
       databaseReference.child(user.getUid()).setValue(studentInformation);


        //dispaly a toast to show that information was saved successfully
        Toast.makeText(this, "Information Saved Successfully", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v == buttonSave){
            saveStudentInformation();
        }


    }
}
