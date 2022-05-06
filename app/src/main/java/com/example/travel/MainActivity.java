package com.example.travel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSingIn;
    private TextView tvForgotPassword,tvRegister;
    private EditText editTextEmail,editTextPassword;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSingIn = (Button) findViewById(R.id.btn_sign_in_login);
        btnSingIn.setOnClickListener(this);

        tvForgotPassword = (TextView) findViewById(R.id.tv_forgot_pass_login);
        tvForgotPassword.setOnClickListener(this);

        tvRegister = (TextView) findViewById(R.id.tv_register_login);
        tvRegister.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.id_email_login);
        editTextPassword = (EditText) findViewById(R.id.id_password_login);

        progressBar = findViewById(R.id.progresBar);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_in_login:
                userLogin();
                break;
            case R.id.tv_forgot_pass_login:
                startActivity(new Intent(MainActivity.this,ForgotPassword.class));
                break;
            case R.id.tv_register_login:
                startActivity(new Intent(MainActivity.this,RegisterUser.class));
                break;

        }
    }

    private void showPassword() {
//        editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editTextPassword.setInputType(129);
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.selectAll();
            editTextPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            editTextPassword.setError("Min password length should be 6 characters ");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this,Home.class));
                        Toast.makeText(MainActivity.this, "Login Successful!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Please check your email to verify account!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Failed to login! please check your credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}