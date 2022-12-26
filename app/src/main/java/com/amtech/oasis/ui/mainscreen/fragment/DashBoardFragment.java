package com.amtech.oasis.ui.mainscreen.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.amtech.oasis.MainActivity;
import com.amtech.oasis.R;
import com.amtech.oasis.model.DashboardObject;
import com.amtech.oasis.model.Login;
import com.amtech.oasis.network.ApiClient;
import com.amtech.oasis.network.ApiInterface;
import com.amtech.oasis.ui.mainscreen.activity.ActivityLogin;
import com.amtech.oasis.util.SharedPreferenceManager;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashBoardFragment extends Fragment{

    private CardView cardCompleted,cardPending,cardAllAssigned;
    private HashMap<String, String> headerMap = new HashMap<String, String>();
    private AppCompatTextView tvCompletedTask,tvPendingTask,tvAssignedTask;
    private DrawerLayout drawerLayout ;
    private NavigationView navigationView;
    private ImageView imvDrawer;
    private String token;
    private boolean isMediaVisible= false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);

        init(view);
        getDashboardData();
        navigationView.bringToFront();

        return view;
    }

    private void init(View view) {
        cardCompleted = view.findViewById(R.id.cardCompleted);
        cardPending = view.findViewById(R.id.cardPending);
        cardAllAssigned = view.findViewById(R.id.cardAllAssigned);
        drawerLayout = view.findViewById(R.id.drawerLayout);
        navigationView = view.findViewById(R.id.navigationDrawer);
        tvCompletedTask = view.findViewById(R.id.tvCompletedTask);
        tvPendingTask = view.findViewById(R.id.tvPendingTask);
        tvAssignedTask = view.findViewById(R.id.tvAssignedTask);
        imvDrawer = view.findViewById(R.id.imvDrawer);
        token = SharedPreferenceManager.getInstance(getActivity()).GetUserToken();

        navigationView.setItemIconTintList(null);

        cardCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_dashBoardFragment_to_completedFragment);
            }
        });

        cardPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_dashBoardFragment_to_pendingTaskFragment);
            }
        });

        cardAllAssigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_dashBoardFragment_to_allAssignedFragment);
            }
        });

        imvDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (item.getItemId()){
                    case R.id.dn_dashboard:
                        drawerLayout.close();
                        /*menu.findItem(R.id.dn_pending).setVisible(false);
                        menu.findItem(R.id.dn_complete).setVisible(false);*/
                        break;

                    case R.id.dn_stores:
                        Navigation.findNavController(requireView()).navigate(R.id.action_dashBoardFragment_to_allAssignedFragment);
                        break;

                    case R.id.dn_pending:
                        Navigation.findNavController(requireView()).navigate(R.id.action_dashBoardFragment_to_pendingTaskFragment);
                        break;

                    case R.id.dn_complete:
                        Navigation.findNavController(requireView()).navigate(R.id.action_dashBoardFragment_to_completedFragment);
                        break;

                    case R.id.dn_task:
                        if (!isMediaVisible) {
//                            menuItemArrow.setRotation(90f);//to rotating arrow to down
                            navigationView.getMenu().setGroupVisible(R.id.dn_all_task, true);
                            isMediaVisible= true;
                        } else {
//                            menuItemArrow.setRotation(0f);
                            navigationView.getMenu().setGroupVisible(R.id.dn_all_task, false);
                            isMediaVisible= false;
                        }
                        return true;

                    case R.id.dn_profile:
                        Navigation.findNavController(requireView()).navigate(R.id.action_dashBoardFragment_to_profileFragment);
                        break;

                    case R.id.dn_signout:
                        Intent intentSignIn = new Intent(getActivity(), ActivityLogin.class);
                        intentSignIn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentSignIn);
                        break;

                }
                return false;
            }
        });
    }

    private void getDashboardData()
    {
        headerMap.put("Authorization","Bearer "+token);
        ApiClient apiClient = new ApiClient();
        ApiInterface service = apiClient.createService(ApiInterface.class);
        Call<DashboardObject> call = service.getDashboard(headerMap);
        call.enqueue(new Callback<DashboardObject>() {

            @Override
            public void onResponse(Call<DashboardObject> call, Response<DashboardObject> response) {

                if (response.isSuccessful()) {
                    DashboardObject loginResponse = response.body();

                    if (loginResponse != null) {
                        tvCompletedTask.setText(loginResponse.getDashboard().getCompletedTask());
                        tvPendingTask.setText(loginResponse.getDashboard().getPendingTask());
                        tvAssignedTask.setText(loginResponse.getDashboard().getAssignedTask());
                    }
                } else {
                    Toast.makeText(getActivity(), "User Id Is Logged In Another Device", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DashboardObject> call, Throwable t) {

                Log.d("ListSize", " - > Error    " + t.getMessage());
            }
        });
    }
}