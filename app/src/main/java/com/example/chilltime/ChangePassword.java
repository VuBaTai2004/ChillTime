package com.example.chilltime;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ChangePassword extends AppCompatActivity {
    private EditText oldPasswordEditText, newPasswordEditText;
    private TextView btnForgotPassword;
    private Button saveNewPassword;
    private FirebaseFirestore db;

    private String username; // Lấy username từ Intent
    private boolean isOldPasswordVisible = false;
    private boolean isNewPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPasswordEditText = findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        btnForgotPassword = findViewById(R.id.forgotPasswordTextView);
        saveNewPassword = findViewById(R.id.savePassword);

        db = FirebaseFirestore.getInstance();

        // Nhận username từ Intent
        username = getIntent().getStringExtra("username");

        ImageView backButton = findViewById(R.id.fpButton);
        backButton.setOnClickListener(v -> finish());

        btnForgotPassword.setOnClickListener(v -> {
            // Mở ForgotPasswordActivity
            startActivity(new Intent(ChangePassword.this, ForgotPasswordActivity.class));
        });

        saveNewPassword.setOnClickListener(v -> {
            String oldPassword = oldPasswordEditText.getText().toString().trim();
            String newPassword = newPasswordEditText.getText().toString().trim();

            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng không bỏ trống các trường", Toast.LENGTH_SHORT).show();
            } else if (newPassword.length() < 6) {
                Toast.makeText(this, "Mật khẩu mới phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            } else {
                validateOldPasswordAndUpdate(oldPassword, newPassword);
            }
        });

        // Gắn toggle ẩn/hiện mật khẩu cho oldPasswordEditText
        oldPasswordEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (oldPasswordEditText.getRight() - oldPasswordEditText.getCompoundDrawables()[2].getBounds().width())) {
                    togglePasswordVisibility(oldPasswordEditText, true);
                    return true;
                }
            }
            return false;
        });

        // Gắn toggle ẩn/hiện mật khẩu cho newPasswordEditText
        newPasswordEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (newPasswordEditText.getRight() - newPasswordEditText.getCompoundDrawables()[2].getBounds().width())) {
                    togglePasswordVisibility(newPasswordEditText, false);
                    return true;
                }
            }
            return false;
        });
    }

    private void validateOldPasswordAndUpdate(String oldPassword, String newPassword) {
        String passwordHash = PasswordUtil.hashPassword(oldPassword);
        String passwordHash1 = PasswordUtil.hashPassword(newPassword);
        db.collection("teachers")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        QuerySnapshot snapshot = task.getResult();
                        String storedPassword = snapshot.getDocuments().get(0).getString("password");
                        // Kiểm tra mật khẩu cũ
                        if (storedPassword.equals(passwordHash)) {
                            // Cập nhật mật khẩu mới
                            String docId = snapshot.getDocuments().get(0).getId();
                            db.collection("teachers").document(docId)
                                    .update("password", passwordHash1)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                        finish(); // Quay lại màn hình trước
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(this, "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Người dùng không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Có lỗi xảy ra khi truy cập dữ liệu", Toast.LENGTH_SHORT).show();
                });
    }

    private void togglePasswordVisibility(EditText editText, boolean isOldPasswordField) {
        if (isOldPasswordField) {
            if (isOldPasswordVisible) {
                // Ẩn mật khẩu
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.visibility_off, 0);
            } else {
                // Hiển thị mật khẩu
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.visibility, 0);
            }
            isOldPasswordVisible = !isOldPasswordVisible;
        } else {
            if (isNewPasswordVisible) {
                // Ẩn mật khẩu
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.visibility_off, 0);
            } else {
                // Hiển thị mật khẩu
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.visibility, 0);
            }
            isNewPasswordVisible = !isNewPasswordVisible;
        }

        // Đặt lại phông chữ và con trỏ
        editText.setTypeface(Typeface.DEFAULT);
        editText.setSelection(editText.getText().length());
    }
}
