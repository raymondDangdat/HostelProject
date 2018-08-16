package com.example.raymond.hostel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;
    private EditText editTextname;
    private EditText editTextMatriculation, editTextPhone;
    private EditText editTextEmergencyNo;
    private Spinner spinnerFaculty;
    private Spinner deparment;

    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Registration Information");

        // if user is already logged in



        buttonRegister =   findViewById(R.id.buttonRegister);
        editTextEmail =  findViewById(R.id.editTextEmail);
        editTextPassword =  findViewById(R.id.editTextPassword);
        textViewSignIn =  findViewById(R.id.textViewSignIn);
        editTextname = findViewById(R.id.editTextname);
        editTextMatriculation = findViewById(R.id.editTextmatriculation);
        editTextPhone = findViewById(R.id.phone);
        editTextEmergencyNo = findViewById(R.id.editTextEmergencyNo);
        spinnerFaculty = findViewById(R.id.faculty);
        deparment = findViewById(R.id.department);


        buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);

    }


    private void registerUser() {
        final String name = editTextname.getText().toString().trim();
        final String matriculation = editTextMatriculation.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String department = deparment.getSelectedItem().toString().trim();
        final String faculty = spinnerFaculty.getSelectedItem().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();
        final String Eno = editTextEmergencyNo.getText().toString().trim();



        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"Your name is Required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(matriculation)){
            Toast.makeText(this, "Matriculation Number is Required", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Phone Number is Required", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(Eno)){
            Toast.makeText(this, "Emergency Number is Required", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            // password is empty
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // user is registered successful
                            // lets start login activity
                                // start profile activity


                            String user_id = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db =  mDatabase.child(user_id);
                            current_user_db.child("Name").setValue(name);
                            current_user_db.child("Email").setValue(email);
                            current_user_db.child("Matriculation Number").setValue(matriculation);
                            current_user_db.child("Student Phone Number").setValue(phone);
                            current_user_db.child("Faculty").setValue(faculty);
                            current_user_db.child("Department").setValue(department);
                            current_user_db.child("Emergency Number").setValue(Eno);

                                Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));


                        }else{
                            Toast.makeText(MainActivity.this, "Could not register please try again", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister){
            registerUser();
        }
        if (v == textViewSignIn){
            finish();
            startActivity(new Intent(this, LoginActivity.class));

        }

    }

}
