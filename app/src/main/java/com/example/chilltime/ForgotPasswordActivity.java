package com.example.chilltime;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button sendEmailButton, backButton;
    private FirebaseFirestore db;
    private EditText usernameEditText;
    String email = "";
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        db = FirebaseFirestore.getInstance();

        sendEmailButton = findViewById(R.id.sendOtpButton);
        usernameEditText = findViewById(R.id.forgotUsernameEditText);

        sendEmailButton.setOnClickListener(v -> {
            username = usernameEditText.getText().toString().trim();

            if (username.isEmpty()) {
                Toast.makeText(ForgotPasswordActivity.this, "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            } else {
                findEmail(username);
            }
        });

        backButton = findViewById(R.id.fpButton);
        backButton.setOnClickListener(v -> finish());
    }

    private void sendEmail() {
        String senderEmail = "wibitoichoi@gmail.com";
        String senderPassword = "noab ypjp dfth rqoe";
        String receiverEmail = email;
        String subject = "Mã OTP từ ứng dụng ChillTime";

        String otp = generateRandomOTP();

        String messageBody = "Mã OTP của bạn là: " + otp;

        new SendMailTask().execute(senderEmail, senderPassword, receiverEmail, subject, messageBody);

        Intent intent = new Intent(ForgotPasswordActivity.this, TypingOtpActivity.class);
        intent.putExtra("otp", otp);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private String generateRandomOTP() {
        Random random = new Random();
        int otp = random.nextInt(100000000);
        return String.format("%08d", otp);
    }

    private class SendMailTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String senderEmail = params[0];
            String senderPassword = params[1];
            String recipientEmail = params[2];
            String subject = params[3];
            String messageBody = params[4];

            try {
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, senderPassword);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject(subject);
                message.setText(messageBody);

                Transport.send(message);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Mail Error", e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(ForgotPasswordActivity.this, "Email sent successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ForgotPasswordActivity.this, "Failed to send email", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void findEmail(String username) {
        String[] collections = {"admins", "teachers", "students"};
        for (String collection : collections) {
            db.collection(collection)
                    .whereEqualTo("username", username)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot snapshot = task.getResult();
                            if (!snapshot.isEmpty()) {
                                String storedEmail = snapshot.getDocuments().get(0).getString("email");
                                email = storedEmail;
                                sendEmail();
                            }
                        } else {
                            Log.e("FirestoreError", "Error checking user", task.getException());
                        }
                    });
        }
    }
}
