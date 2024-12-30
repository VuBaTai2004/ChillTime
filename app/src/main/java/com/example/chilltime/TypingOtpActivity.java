package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chilltime.R;

public class TypingOtpActivity extends AppCompatActivity {
    private EditText otpEditText;
    private Button confirmOtpButton, backButton;
    private String receivedOTP;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing_otp);

        otpEditText = findViewById(R.id.otpEditText);
        confirmOtpButton = findViewById(R.id.confirmOtpButton);
        backButton = findViewById(R.id.otpButton);

        receivedOTP = getIntent().getStringExtra("otp");
        username = getIntent().getStringExtra("username");

        confirmOtpButton.setOnClickListener(v -> {
            String enteredOTP = otpEditText.getText().toString().trim();

            if (enteredOTP.equals(receivedOTP)) {
                Intent intent = new Intent(TypingOtpActivity.this, RecreatePasswordActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            } else {
                Toast.makeText(TypingOtpActivity.this, "OTP không đúng", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> finish());
    }
}
