package com.example.travel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextView tvLogin;
    private EditText editTextName,editTextEmail,editTextPassword,editTextConfirmPassword;
    private Button btnRegister;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        tvLogin = findViewById(R.id.tv_sign_in_register);
        tvLogin.setOnClickListener(this);

        editTextName = findViewById(R.id.id_name_register);
        editTextEmail = findViewById(R.id.id_email_register);
        editTextPassword = findViewById(R.id.id_password_register);
        editTextConfirmPassword = findViewById(R.id.id_confirm_password_register);

        btnRegister = findViewById(R.id.btn_register_register);
        btnRegister.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar_Register);

        mAuth= FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_sign_in_register:
                startActivity(new Intent(RegisterUser.this,MainActivity.class));
                break;
            case R.id.btn_register_register:
                registerUser();
                break;
        }
    }

    private void registerUser() {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (name.isEmpty()){
                editTextName.setError("Name is required!");
                editTextName.requestFocus();
                return;
            }
            if (email.isEmpty()){
                editTextEmail.setError("Email is required!");
                editTextEmail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                editTextEmail.setError("Please provide valid email!");
                return;
            }
            if (password.isEmpty()){
                editTextPassword.setError("Password is required!");
                editTextPassword.requestFocus();
                return;
            }
            if (password.length()<6){
                editTextPassword.setError("Min password length should be 6 character!");
                editTextPassword.requestFocus();
                return;
            }
            if (confirmPassword.isEmpty()){
                editTextConfirmPassword.setError("Confirm password is required!");
                editTextConfirmPassword.requestFocus();
                return;
            }
            if (!confirmPassword.equals(password)){
                editTextConfirmPassword.setError("Confirm password is not correct!");
                editTextConfirmPassword.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(name,email,password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(RegisterUser.this, "User has been register successfully", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                    }
                                    else {
                                        Toast.makeText(RegisterUser.this, "Failed to register! try again!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }


                            });
                            Intent i = new Intent(RegisterUser.this,MainActivity.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
    }
}