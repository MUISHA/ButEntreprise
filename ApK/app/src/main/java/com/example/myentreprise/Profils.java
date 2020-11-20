package com.example.myentreprise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profils extends AppCompatActivity {
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profils);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profil");
        actionBar.setDisplayShowCustomEnabled(true);
        auth = FirebaseAuth.getInstance();
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        CheckUserStatut();
    }
    private  void CheckUserStatut(){
        FirebaseUser user = auth.getCurrentUser();
        if (user != null){

        }else {
            startActivity(new Intent(Profils.this, MainActivity.class));
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int index = item.getItemId();
        if (index == R.id.action_logout){
            auth.signOut();
            CheckUserStatut();
        }
        return super.onOptionsItemSelected(item);

    }
}