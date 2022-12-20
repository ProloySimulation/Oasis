package com.amtech.oasis.ui.mainscreen.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.amtech.oasis.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;


public class DashBoardFragment extends Fragment{

    private CardView cardCompleted,cardPending,cardAllAssigned;
    private DrawerLayout drawerLayout ;
    private NavigationView navigationView;
    private ImageView imvDrawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);

        init(view);
        navigationView.bringToFront();

        return view;
    }

    private void init(View view) {
        cardCompleted = view.findViewById(R.id.cardCompleted);
        cardPending = view.findViewById(R.id.cardPending);
        cardAllAssigned = view.findViewById(R.id.cardAllAssigned);
        drawerLayout = view.findViewById(R.id.drawerLayout);
        navigationView = view.findViewById(R.id.navigationDrawer);
        imvDrawer = view.findViewById(R.id.imvDrawer);

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

                switch (id){
                    case R.id.dn_dashboard:
                        drawerLayout.close();
                        break;

                    case R.id.dn_stores:
                        Navigation.findNavController(requireView()).navigate(R.id.action_dashBoardFragment_to_allAssignedFragment);
                        break;

                    case R.id.dn_task:

                        break;

                    case R.id.dn_signout:

                        break;

                }
                return false;
            }
        });
    }
}