package com.amtech.oasis.ui.mainscreen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amtech.oasis.MainActivity;
import com.amtech.oasis.R;

public class ActivityLogin extends AppCompatActivity {

    private AppCompatButton btnSignIn;
    private AppCompatTextView tvForgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewInit();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityResetPassword.class);
                startActivity(intent);
            }
        });
    }

    private void viewInit() {
        btnSignIn = findViewById(R.id.btnLoginSignIn);
        tvForgotPass = findViewById(R.id.tvLoginForgotPassword);
    }
}