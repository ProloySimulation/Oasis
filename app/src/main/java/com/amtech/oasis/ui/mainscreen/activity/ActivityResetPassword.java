package com.amtech.oasis.ui.mainscreen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;
import android.view.View;

import com.amtech.oasis.R;

public class ActivityResetPassword extends AppCompatActivity {

    private AppCompatImageView imvBack;

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
    }

    private void viewInit() {
        imvBack = findViewById(R.id.imvBackChangePassword);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}