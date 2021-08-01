package com.rootzero.parkinator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private EditText editFullName, editEmail, editPassword, editPasswordConfirmation, editPhoneNumber;
    private ProgressBar progressBar;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("CREATION", "registerUser called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();

        TextView banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        TextView registerUser = (Button) findViewById(R.id.registerButton);
        registerUser.setOnClickListener(this);

        editFullName = (EditText) findViewById(R.id.editFullName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editPasswordConfirmation = (EditText) findViewById(R.id.editPasswordConfirmation);
        editPhoneNumber = (EditText) findViewById(R.id.editPhoneNumber);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
//        Log.d(TAG, "onClick: ", v);
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerButton:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        Log.d("STATE", "registerUser called");
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String passwordConfirmation = editPasswordConfirmation.getText().toString().trim();
        String phoneNumber = editPhoneNumber.getText().toString().trim();
        String fullName = editFullName.getText().toString().trim();

        if (fullName.isEmpty()) {
            editFullName.setError("Full Name Is Required!");
            editFullName.requestFocus();
            return;
        }

        if (phoneNumber.isEmpty()) {
            editPhoneNumber.setError("Phone Number Is Required!");
            editPhoneNumber.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editEmail.setError("Email Is Required!");
            editEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please Provide Valid Email!");
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError("Please Type A Password!");
            editPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editPassword.setError("Minimum Password Length - 6 Chars");
            editPassword.requestFocus();
            return;
        }
        if (passwordConfirmation.isEmpty()) {
            editPasswordConfirmation.setError("Please Confirm Your Password!");
            editPasswordConfirmation.requestFocus();
            return;
        }
        if (!passwordConfirmation.equals(password)) {
            editPasswordConfirmation.setError("Passwords Do Not Match!");
            editPasswordConfirmation.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                User user = new User(fullName, phoneNumber, email);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterUser.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                        // redirect to login layout
                                        else {
                                            Toast.makeText(RegisterUser.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }

                                    }
                                });
                            } else {
                                Toast.makeText(RegisterUser.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
        } catch(Exception e) {
            Log.d("STATE", "registerUser called", e);
        }

    }
}