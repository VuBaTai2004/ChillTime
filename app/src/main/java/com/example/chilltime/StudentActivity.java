package com.example.chilltime;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student);

        String username = getIntent().getStringExtra("username");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_schedule) {
                    loadFragment(new StudentScheduleFragment(), false, username);
                }
                else if (itemId == R.id.nav_class) {
                    loadFragment(new StudentClassFragment(), false, username);
                }
                else if (itemId == R.id.nav_transcript) {
                    loadFragment(new StudentTranscriptFragment(), false, username);
                }
                else if (itemId == R.id.nav_notification) {
                    loadFragment(new StudentNotificationFragment(), false, username);
                }
                else if (itemId == R.id.nav_profile) {
                    loadFragment(new StudentProfileFragment(), false, username);
                }
                return true;
            }
        });

        loadFragment(new StudentScheduleFragment(), true, username);

    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized, String username){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (fragment instanceof StudentProfileFragment && username != null) {
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            fragment.setArguments(bundle);
        }

        if (fragment instanceof StudentClassFragment && username != null) {
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            fragment.setArguments(bundle);
        }

        if (fragment instanceof StudentScheduleFragment && username != null) {
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            fragment.setArguments(bundle);
        }

        if (fragment instanceof StudentNotificationFragment && username != null) {
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            fragment.setArguments(bundle);
        }

        if (fragment instanceof StudentTranscriptFragment && username != null) {
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            fragment.setArguments(bundle);
        }

        if (isAppInitialized) {
            fragmentTransaction.add(R.id.fragment_container, fragment);
        }
        else fragmentTransaction.replace(R.id.fragment_container, fragment);

        fragmentTransaction.commit();
    }
}