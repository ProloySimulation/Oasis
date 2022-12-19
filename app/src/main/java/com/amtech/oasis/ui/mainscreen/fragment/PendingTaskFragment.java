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
import com.amtech.oasis.ui.mainscreen.adapter.CompletedTaskAdapter;


public class PendingTaskFragment extends Fragment {

    private RecyclerView recyclerView;
    private CompletedTaskAdapter adapter;
    private AppCompatImageView imvBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_task, container, false);

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

    private void init(View view)
    {
        recyclerView = view.findViewById(R.id.recyclerPending);
        imvBack = view.findViewById(R.id.imvBackPendingTask);
    }

    private void setRecycler()
    {
        adapter = new CompletedTaskAdapter(getActivity(),"pending", new CompletedTaskAdapter.ClickListener() {
            @Override
            public void itemClickCompleted(int position) {
            }

            @Override
            public void itemClickPending(int position) {
                Navigation.findNavController(requireView()).navigate(R.id.action_pendingTaskFragment_to_pendingDetailFragment);
            }

            @Override
            public void itemClickAllAssign(int position) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}