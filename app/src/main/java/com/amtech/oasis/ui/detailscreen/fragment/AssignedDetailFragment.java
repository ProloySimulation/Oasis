package com.amtech.oasis.ui.detailscreen.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amtech.oasis.R;
import com.amtech.oasis.model.AssignStorArr;
import com.amtech.oasis.model.StoreDataObj;
import com.amtech.oasis.model.Stores;
import com.amtech.oasis.network.ApiClient;
import com.amtech.oasis.network.ApiInterface;
import com.amtech.oasis.util.SharedPreferenceManager;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AssignedDetailFragment extends Fragment {

    private AppCompatTextView tvStoreName,tvStoreLat,tvStoreLong,tvStoreCountry,tvStoreRegion;
    private AppCompatImageView imvBack;
    private AppCompatButton btnBack;
    private HashMap<String, String> headerMap = new HashMap<String, String>();
    private String token,storeId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assigned_detail, container, false);

        init(view);
        getAssignedStore();

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        return view;
    }

    private void getAssignedStore()
    {
        headerMap.put("Authorization","Bearer "+token);
        ApiClient apiClient = new ApiClient();
        ApiInterface service = apiClient.createService(ApiInterface.class);
        Call<StoreDataObj> call = service.getStoreInfo(headerMap,storeId);
        call.enqueue(new Callback<StoreDataObj>() {

            @Override
            public void onResponse(Call<StoreDataObj> call, Response<StoreDataObj> response) {

                if (response.isSuccessful()) {
                    StoreDataObj storeDataObj = response.body();

                    if (storeDataObj != null) {
                        setUI(storeDataObj.getAssignStorArr().get(0).getTaskName(),storeDataObj.getAssignStorArr().get(0).getStoreLat(),storeDataObj.getAssignStorArr().get(0).getStoreLong(),
                                storeDataObj.getAssignStorArr().get(0).getCountry(),storeDataObj.getAssignStorArr().get(0).getRegion());
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "NetWork Problem", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "User Id Is Logged In Another Device", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<StoreDataObj> call, Throwable t) {

                Log.d("ListSize", " - > Error    " + t.getMessage());
            }
        });
    }

    private void init(View view) {
        tvStoreName = view.findViewById(R.id.tvAssignStoreDetailName);
        tvStoreLat = view.findViewById(R.id.tvAssignStoreDetailLat);
        tvStoreLong = view.findViewById(R.id.tvAssignStoreDetailLong);
        tvStoreRegion = view.findViewById(R.id.tvAssignStoreDetailRegion);
        tvStoreCountry = view.findViewById(R.id.tvAssignStoreDetailCountry);
        imvBack = view.findViewById(R.id.imvBackAssignedDetail);
        btnBack = view.findViewById(R.id.btnAssignedBack);
        storeId = getArguments().getString("STOREID");
        token = SharedPreferenceManager.getInstance(getActivity()).GetUserToken();
    }

    private void setUI(String name,String lat,String longitute,String country,String region)
    {
        tvStoreName.setText(name);
        tvStoreLat.setText(lat);
        tvStoreLong.setText(longitute);
        tvStoreCountry.setText(country);
        tvStoreRegion.setText(region);
    }
}