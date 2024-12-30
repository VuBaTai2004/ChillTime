package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class RecreatePasswordActivity extends AppCompatActivity {

    private EditText passwordEditText, confirmPasswordEditText;
    private Button confirmButton;
    private FirebaseFirestore db;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recreate_password);

        db = FirebaseFirestore.getInstance();

        passwordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        confirmButton = findViewById(R.id.confirmButton);

        username = getIntent().getStringExtra("username");

        confirmButton.setOnClickListener(v -> {
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RecreatePasswordActivity.this, "Vui lòng không để trống mật khẩu", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(RecreatePasswordActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            } else {
                password = PasswordUtil.hashPassword(password);
                updatePassword(username, password);
            }
        });
    }

    private void updatePassword(String username, String newPassword) {
        String[] collections = {"admins", "teachers", "students"};

        for (String collection : collections) {
            db.collection(collection)
                    .whereEqualTo("username", username)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                String documentId = task.getResult().getDocuments().get(0).getId();
                                db.collection(collection)
                                        .document(documentId)
                                        .update("password", newPassword)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(RecreatePasswordActivity.this, "Mật khẩu đã được thay đổi", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(RecreatePasswordActivity.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(RecreatePasswordActivity.this, "Lỗi khi thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        } else {
                            Toast.makeText(RecreatePasswordActivity.this, "Lỗi kết nối với Firestore", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
