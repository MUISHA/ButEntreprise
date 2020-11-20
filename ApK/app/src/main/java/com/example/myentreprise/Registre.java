package com.example.myentreprise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaCodec;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registre extends AppCompatActivity  {
    private FirebaseAuth auth;
    private Button registre_btn;
    private EditText mailEt,passEt;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registre);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registre In...");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Acount");
        actionBar.setDisplayShowCustomEnabled(true);
        mailEt = findViewById(R.id.mailEt);
        passEt = findViewById(R.id.passEt);
        registre_btn = findViewById(R.id.registre_btn);
        registre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mailEt.getText().toString().trim();
                String password = passEt.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mailEt.setError("Inavalid E-mail");
                    mailEt.setFocusable(true);
                }else if (password.length() < 6){
                    passEt.setError("Password length at least 6 charcters");
                    passEt.setFocusable(true);
                }else {
                    SingInRegistre(email, password);
                }
            }
        });
        final TextView loginR = findViewById(R.id.loginR);
        loginR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registre.this, Login.class));
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        //updateUI(currentUser);
    }

   private void SingInRegistre(String email,String password) {
        progressDialog.show();
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                FirebaseUser user = auth.getCurrentUser();
                                Toast.makeText(Registre.this, "\n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registre.this, Profils.class));
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Registre.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Registre.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
}