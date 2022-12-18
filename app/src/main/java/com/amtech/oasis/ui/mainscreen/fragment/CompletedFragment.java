package com.amtech.oasis.ui.mainscreen.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amtech.oasis.R;
import com.amtech.oasis.ui.mainscreen.adapter.CompletedTaskAdapter;


public class CompletedFragment extends Fragment {

    private RecyclerView recyclerCompleted;
    private CompletedTaskAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed, container, false);

        init(view);
        setRecycler();

        return view;
    }

    private void init(View view) {
        recyclerCompleted = view.findViewById(R.id.recyclerCompletedTask);
    }

    private void setRecycler()
    {
        adapter = new CompletedTaskAdapter(getActivity());
        recyclerCompleted.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerCompleted.setItemAnimator(new DefaultItemAnimator());
        recyclerCompleted.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}