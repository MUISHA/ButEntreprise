package com.example.myentreprise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button login_btn;
    private ProgressDialog progressDialog;
    private EditText mailEtL,passEtL;
    private TextView RegistreR,forge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        progressDialog = new ProgressDialog(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
        actionBar.setDisplayShowCustomEnabled(true);
        auth = FirebaseAuth.getInstance();
        mailEtL = findViewById(R.id.mailEtL);
        passEtL = findViewById(R.id.passEtL);
        login_btn = findViewById(R.id.login_btn);
        forge = findViewById(R.id.forge);
        forge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowRecoverPasswordDialog();
            }
        });
        RegistreR = findViewById(R.id.RegistreR);
        RegistreR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registre.class));
                finish();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mailEtL.getText().toString().trim();
                String password = passEtL.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    mailEtL.setError("Inavalid E-mail");
                    mailEtL.setFocusable(true);
                }else if (password.length() < 6){
                    passEtL.setError("Password length at least 6 charcters");
                    passEtL.setFocusable(true);
                }else {
                    SingChekingLogin(mail, password);
                }
            }
        });
    }

    private void ShowRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout = new LinearLayout(this);
        EditText emailEtL = new EditText(this);
        emailEtL.setHint("E-mail");
        emailEtL.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailEtL);
        emailEtL.setMaxEms(30);
        
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = emailEtL.getText().toString().trim();
                BeginRecover(email);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void BeginRecover(String email) {
        progressDialog.setTitle("Recover");
        progressDialog.show();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "E-mail sent", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(Login.this, Login.class));
                        }else {
                            Toast.makeText(Login.this, "E-mail sent Erreur...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SingChekingLogin(String email, String password) {
        progressDialog.show();
             auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(Login.this, "Authentication Succeful...",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, Profils.class));
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     progressDialog.dismiss();
                     Toast.makeText(Login.this, ""+e.getMessage(),
                             Toast.LENGTH_SHORT).show();
                 }
             });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}