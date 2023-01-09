package com.amtech.oasis.ui.mainscreen.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amtech.oasis.MainActivity;
import com.amtech.oasis.R;
import com.amtech.oasis.model.Login;
import com.amtech.oasis.model.ProfileObject;
import com.amtech.oasis.network.ApiClient;
import com.amtech.oasis.network.ApiInterface;
import com.amtech.oasis.ui.mainscreen.activity.ActivityLogin;
import com.amtech.oasis.ui.mainscreen.activity.ActivityResetPassword;
import com.amtech.oasis.util.SharedPreferenceManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    private AppCompatEditText etName,etEmail,etAssignedName;
    private AppCompatImageView imvBack;
    private AppCompatTextView tvChangePassword;
    private HashMap<String, String> headerMap = new HashMap<String, String>();
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        init(view);
        getProfile();

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityResetPassword.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void init(View view) {
        imvBack = view.findViewById(R.id.imvBackProfile);
        tvChangePassword = view.findViewById(R.id.tvProfileChangePassword);
        etName = view.findViewById(R.id.tvProfileName);
        etEmail = view.findViewById(R.id.etProfileUserEmail);
        etAssignedName = view.findViewById(R.id.etAssignedName);

        token = SharedPreferenceManager.getInstance(getActivity()).GetUserToken();

        etEmail.setEnabled(false);
        etAssignedName.setEnabled(false);
    }

    private void getProfile()
    {
        headerMap.put("Authorization","Bearer "+token);
        ApiClient apiClient = new ApiClient();
        ApiInterface service = apiClient.createService(ApiInterface.class);
        Call<ProfileObject> call = service.getProfile(headerMap);
        call.enqueue(new Callback<ProfileObject>() {

            @Override
            public void onResponse(Call<ProfileObject> call, Response<ProfileObject> response) {

                if (response.isSuccessful()) {
                    ProfileObject loginResponse = response.body();

                    if (loginResponse != null) {
                        etName.setText(loginResponse.getProfile().getName());
                        etEmail.setText(loginResponse.getProfile().getEmail());
                        etAssignedName.setText(loginResponse.getProfile().getAssignedName());
                    }
                } else {
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfileObject> call, Throwable t) {

                Log.d("ListSize", " - > Error    " + t.getMessage());
            }
        });
    }
}