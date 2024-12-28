package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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

public class AdminActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_teacher) {
                    loadFragment(new AdminTeacherFragment(), false);
                }
                else if (itemId == R.id.nav_student) {
                    loadFragment(new AdminStudentFragment(), false);
                }
                else if (itemId == R.id.nav_subject) {
                    loadFragment(new AdminSubjectFragment(), false);
                }
                else if (itemId == R.id.nav_setting) {
                    loadFragment(new AdminSettingFragment(), false);
                }
                return true;
            }
        });

        loadFragment(new AdminTeacherFragment(), true);


    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialized){
            fragmentTransaction.add(R.id.fragment_container, fragment);
        }
        else fragmentTransaction.replace(R.id.fragment_container, fragment);

        fragmentTransaction.commit();
    }
}