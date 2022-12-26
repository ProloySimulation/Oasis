package com.amtech.oasis.ui.mainscreen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amtech.oasis.MainActivity;
import com.amtech.oasis.R;
import com.amtech.oasis.model.Login;
import com.amtech.oasis.network.ApiClient;
import com.amtech.oasis.network.ApiInterface;
import com.amtech.oasis.util.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity {

    private AppCompatButton btnSignIn;
    private ProgressBar progressBar;
    private AppCompatTextView tvForgotPass;
    private AppCompatEditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewInit();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
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
        etEmail = findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);
        btnSignIn = findViewById(R.id.btnLoginSignIn);
        tvForgotPass = findViewById(R.id.tvLoginForgotPassword);
        progressBar = findViewById(R.id.progressBarLogin);
    }

    private void signIn() {

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        Login login = new Login(email, password);

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email Or Password Missing", Toast.LENGTH_SHORT).show();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            ApiClient apiClient = new ApiClient();
            ApiInterface service = apiClient.createService(ApiInterface.class);
            Call<Login> call = service.loginUser(login);
            call.enqueue(new Callback<Login>() {

                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {

                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        Login loginResponse = response.body();

                        if (loginResponse != null) {
                            String token = loginResponse.getLoginToken();
                            SharedPreferenceManager.getInstance(getApplicationContext()).SetUserToken(token);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ActivityLogin.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {

                    progressBar.setVisibility(View.GONE);
                    Log.d("ListSize", " - > Error    " + t.getMessage());
                }
            });
        }
    }
}