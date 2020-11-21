package com.example.myentreprise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myentreprise.fragment.AppelFragment;
import com.example.myentreprise.fragment.HomeFragment;
import com.example.myentreprise.fragment.MenuFragment;
import com.example.myentreprise.fragment.ProfilsFragment;
import com.example.myentreprise.fragment.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashbord extends AppCompatActivity {
    private FirebaseAuth auth;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashbord);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Profil");
        actionBar.setDisplayShowCustomEnabled(true);
        auth = FirebaseAuth.getInstance();
        BottomNavigationView navigationView = findViewById(R.id.navigationBtm);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        actionBar.setTitle("Home");
        AppelFragment appelFragment = new AppelFragment();
        FragmentTransaction transactionAp = getSupportFragmentManager()
                .beginTransaction();
        transactionAp.replace(R.id.containerFrgm, appelFragment, "");
        transactionAp.commit();
    }
    BottomNavigationView.OnNavigationItemSelectedListener selectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.nav_home :
                    actionBar.setTitle("Home");
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction transactionH = getSupportFragmentManager()
                            .beginTransaction();
                    transactionH.replace(R.id.containerFrgm, homeFragment, "");
                    transactionH.commit();
                    return true;
                case R.id.nav_appel :
                    actionBar.setTitle("Appels");
                    AppelFragment appelFragment = new AppelFragment();
                    FragmentTransaction transactionAp = getSupportFragmentManager()
                            .beginTransaction();
                    transactionAp.replace(R.id.containerFrgm, appelFragment, "");
                    transactionAp.commit();
                    return true;
                case R.id.nav_profil :
                    actionBar.setTitle("Profils");
                    ProfilsFragment profilsFragment = new ProfilsFragment();
                    FragmentTransaction transactionProf = getSupportFragmentManager()
                            .beginTransaction();
                    transactionProf.replace(R.id.containerFrgm, profilsFragment, "");
                    transactionProf.commit();
                    return true;
                case R.id.nav_settings :
                    actionBar.setTitle("Settings");
                    SettingsFragment settingsFragment = new SettingsFragment();
                    FragmentTransaction transactionSetg = getSupportFragmentManager()
                            .beginTransaction();
                    transactionSetg.replace(R.id.containerFrgm, settingsFragment, "");
                    transactionSetg.commit();
                    return true;
                     case R.id.nav_menu :
                    actionBar.setTitle("Settings");
                    MenuFragment menuFragment = new MenuFragment();
                    FragmentTransaction transactionMenue = getSupportFragmentManager()
                            .beginTransaction();
                    transactionMenue.replace(R.id.containerFrgm, menuFragment, "");
                    transactionMenue.commit();
                    return true;


            }
            return false;
        }
    };
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
            startActivity(new Intent(Dashbord.this, MainActivity.class));
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
        if (index == R.id.action_exit){

        }
        return super.onOptionsItemSelected(item);

    }
}