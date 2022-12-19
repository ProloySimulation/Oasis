package com.amtech.oasis.ui.mainscreen.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amtech.oasis.R;
import com.google.android.material.navigation.NavigationView;


public class DashBoardFragment extends Fragment implements View.OnClickListener {

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
//        navigationView.bringToFront();

        return view;
    }

    private void init(View view) {
        cardCompleted = view.findViewById(R.id.cardCompleted);
        cardPending = view.findViewById(R.id.cardPending);
        cardAllAssigned = view.findViewById(R.id.cardAllAssigned);
//        drawerLayout = view.findViewById(R.id.drawerLayout);
//        navigationView = view.findViewById(R.id.navigationDrawer);
//        imvDrawer = view.findViewById(R.id.imvDrawer);

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
    }

    @Override
    public void onClick(View view) {

        if(view == imvDrawer)
        {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }
}