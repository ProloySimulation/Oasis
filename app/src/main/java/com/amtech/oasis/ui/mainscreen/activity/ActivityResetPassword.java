package com.amtech.oasis.ui.mainscreen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amtech.oasis.MainActivity;
import com.amtech.oasis.R;
import com.amtech.oasis.model.Login;
import com.amtech.oasis.model.Password;
import com.amtech.oasis.network.ApiClient;
import com.amtech.oasis.network.ApiInterface;
import com.amtech.oasis.util.SharedPreferenceManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityResetPassword extends AppCompatActivity {

    private AppCompatEditText etPass,etNewPass,etConfirmPass;
    private AppCompatButton btnChangePass;
    private AppCompatImageView imvBack;
    private HashMap<String, String> headerMap = new HashMap<String, String>();
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        viewInit();

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void viewInit() {
        etPass = findViewById(R.id.etOldPass);
        etNewPass = findViewById(R.id.etNewPass);
        btnChangePass = findViewById(R.id.btnDonePassChange);
        etConfirmPass = findViewById(R.id.etConfirmNewPass);
        imvBack = findViewById(R.id.imvBackChangePassword);
        token = SharedPreferenceManager.getInstance(getApplicationContext()).GetUserToken();
    }

    private void changePassword()
    {
        headerMap.put("Authorization","Bearer "+token);
        String oldPassword = etPass.getText().toString();
        String newPassword = etNewPass.getText().toString();
        String confirmPassword = etConfirmPass.getText().toString();

        Password password = new Password(oldPassword,newPassword,confirmPassword);

        ApiClient apiClient = new ApiClient();
        ApiInterface service = apiClient.createService(ApiInterface.class);
        Call<Password> call = service.passwordChange(headerMap,password);
        call.enqueue(new Callback<Password>() {

            @Override
            public void onResponse(Call<Password> call, Response<Password> response) {

                if (response.isSuccessful()) {

                    Password loginResponse = response.body();

                    if (loginResponse != null) {
                        Toast.makeText(ActivityResetPassword.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityResetPassword.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Password> call, Throwable t) {

                Log.d("ListSize", " - > Error    " + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}