package com.example.chilltime;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
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

    // Biến trạng thái hiển thị mật khẩu
    private boolean isPasswordVisible = false;

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

        // Thêm sự kiện hiển thị/ẩn mật khẩu khi nhấn vào drawableEnd
        etPassword.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Kiểm tra xem người dùng có nhấn vào drawableEnd không
                if (event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[2].getBounds().width())) {
                    togglePasswordVisibility();
                    return true;
                }
            }
            return false;
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

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Nếu mật khẩu đang hiển thị, chuyển sang ẩn
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            etPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.visibility_off, 0);
        } else {
            // Nếu mật khẩu đang ẩn, chuyển sang hiển thị
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            etPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.visibility, 0);
        }
        // Đặt lại phông chữ và con trỏ
        etPassword.setTypeface(Typeface.DEFAULT);
        etPassword.setSelection(etPassword.getText().length());
        isPasswordVisible = !isPasswordVisible;
    }
}
