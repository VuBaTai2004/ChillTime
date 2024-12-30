package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private FirebaseFirestore db;
    private TextView btnForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.usernameEditText);
        etPassword = findViewById(R.id.passwordEditText);
        btnLogin = findViewById(R.id.loginButton);
        btnForgotPassword = findViewById(R.id.forgotPasswordTextView);

        db = FirebaseFirestore.getInstance();

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            } else {
                authenticateUser(username, password);
            }
        });

        btnForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void authenticateUser(String username, String password) {
        String[] collections = {"admins", "teachers", "students"};
        String passwordHash = PasswordUtil.hashPassword(password);

        for (String collection : collections) {
            db.collection(collection)
                    .whereEqualTo("username", username)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot snapshot = task.getResult();
                            if (!snapshot.isEmpty()) {
                                String storedHash = snapshot.getDocuments().get(0).getString("password");
                                if (passwordHash.equals(storedHash)) {
                                    navigateToActivity(collection);
                                } else {
                                    Toast.makeText(this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Log.e("FirestoreError", "Error checking user", task.getException());
                        }
                    });
        }
    }

    private void navigateToActivity(String collection) {
        Intent intent;
        switch (collection) {
            case "admins":
                intent = new Intent(this, AdminActivity.class);
                break;
            case "teachers":
                intent = new Intent(this, TeacherActivity.class);
                break;
            case "students":
                intent = new Intent(this, StudentActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + collection);
        }
        startActivity(intent);
    }
}
