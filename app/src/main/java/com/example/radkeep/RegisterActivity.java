package com.example.radkeep;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.radkeep.DataBase.RoomDB;
import com.example.radkeep.Models.User;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail, etPassword, etConfirmPassword, etName;
    private Button btnRegister;
    private TextView tvLogin;
    private RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = RoomDB.getInstance(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etName = findViewById(R.id.eetName);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> registerUser());
        tvLogin.setOnClickListener(v -> startLoginActivity());
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String name = etName.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            User existingUser = database.mainDAO().getUserByEmail(email);
            runOnUiThread(() -> {
                if (existingUser != null) {
                    Toast.makeText(RegisterActivity.this, "Email уже зарегистрирован", Toast.LENGTH_SHORT).show();
                } else {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setPassword(PasswordUtils.hashPassword(password));
                    newUser.setName(name);

                    new Thread(() -> {
                        database.mainDAO().insertUser(newUser);
                        runOnUiThread(() -> {
                            Toast.makeText(RegisterActivity.this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
                            startLoginActivity();
                        });
                    }).start();
                }
            });
        }).start();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}