package com.example.myentreprise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login_btn, registre_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Acount");
        actionBar.setDisplayShowCustomEnabled(true);
        login_btn = findViewById(R.id.login_btn);
        registre_btn = findViewById(R.id.registre_btn);
        login_btn.setOnClickListener((View.OnClickListener) this);
        registre_btn.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        int index = v.getId();
        switch (index){
            case R.id.login_btn : startActivity(new Intent(this, Login.class)); break;
            case R.id.registre_btn : startActivity(new Intent(this, Registre.class)); break;
            default:
                Toast.makeText(this, "Erreur select...!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}