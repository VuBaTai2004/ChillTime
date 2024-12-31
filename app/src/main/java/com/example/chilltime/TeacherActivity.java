package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class TeacherActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher);

        String username = getIntent().getStringExtra("username");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_schedule) {
                    loadFragment(new TeacherScheduleFragment(), false, null);
                } else if (itemId == R.id.nav_class) {
                    loadFragment(new TeacherClassFragment(), false, null);
                } else if (itemId == R.id.nav_profile) {
                    loadFragment(new TeacherProfileFragment(), false, username);
                }
                return true;
            }
        });

        loadFragment(new TeacherScheduleFragment(), true, null);

    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized, String username){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (fragment instanceof TeacherProfileFragment && username != null) {
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