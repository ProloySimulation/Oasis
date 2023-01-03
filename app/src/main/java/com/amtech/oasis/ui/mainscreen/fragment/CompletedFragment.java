package com.amtech.oasis.ui.mainscreen.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amtech.oasis.R;
import com.amtech.oasis.model.AssignStorArr;
import com.amtech.oasis.model.AssignStore;
import com.amtech.oasis.model.Stores;
import com.amtech.oasis.model.Tasks;
import com.amtech.oasis.ui.mainscreen.adapter.CompletedTaskAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CompletedFragment extends Fragment {

    private RecyclerView recyclerCompleted;
    private CompletedTaskAdapter adapter;
    private AppCompatImageView imvBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed, container, false);

        init(view);
        setRecycler();

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });
        return view;
    }

    private void init(View view) {
        recyclerCompleted = view.findViewById(R.id.recyclerCompletedTask);
        imvBack = view.findViewById(R.id.imvBackCompletedTasks);
    }

    private void setRecycler()
    {
        List<Stores> assignStores = new ArrayList<>();
        List<AssignStorArr> pendingTask = new ArrayList<>();
        adapter = new CompletedTaskAdapter(getActivity(),"completed",assignStores,pendingTask, new CompletedTaskAdapter.ClickListener() {
            @Override
            public void itemClickCompleted(int position) {
                Navigation.findNavController(requireView()).navigate(R.id.action_completedFragment_to_detailFragment);
            }

            @Override
            public void itemClickPending(int position) {

            }

            @Override
            public void itemClickAllAssign(int position) {

            }
        });
        recyclerCompleted.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerCompleted.setItemAnimator(new DefaultItemAnimator());
        recyclerCompleted.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}