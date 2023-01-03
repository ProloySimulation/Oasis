package com.amtech.oasis.ui.detailscreen.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amtech.oasis.R;
import com.amtech.oasis.model.AssignStore;
import com.amtech.oasis.model.CheckList;
import com.amtech.oasis.model.StoreDataObj;
import com.amtech.oasis.model.Tasks;
import com.amtech.oasis.network.ApiClient;
import com.amtech.oasis.network.ApiInterface;
import com.amtech.oasis.ui.detailscreen.adapter.PendingCheckListAdapter;
import com.amtech.oasis.ui.mainscreen.adapter.CompletedTaskAdapter;
import com.amtech.oasis.util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PendingDetailFragment extends Fragment {

    private AppCompatImageView imvBack;
    private AppCompatTextView tvTaskName,tvTaskDate;
    private RecyclerView recyclerView;
    private String taskId,token;
    private PendingCheckListAdapter adapter;
    private HashMap<String, String> headerMap = new HashMap<String, String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pending_detail, container, false);

        init(view);
        getPendingTask();

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
        imvBack = view.findViewById(R.id.imvBackPendingDetail);
        tvTaskName = view.findViewById(R.id.tvPendingTaskDetailName);
        tvTaskDate = view.findViewById(R.id.tvPendingTaskDetailDate);
        recyclerView = view.findViewById(R.id.recyclerPendingCheckList);
        taskId = getArguments().getString("TASKID");
        token = SharedPreferenceManager.getInstance(getActivity()).GetUserToken();
    }

    private void getPendingTask()
    {
        headerMap.put("Authorization","Bearer "+token);
        ApiClient apiClient = new ApiClient();
        ApiInterface service = apiClient.createService(ApiInterface.class);
        Call<StoreDataObj> call = service.getPendingTask(headerMap,taskId);
        call.enqueue(new Callback<StoreDataObj>() {

            @Override
            public void onResponse(Call<StoreDataObj> call, Response<StoreDataObj> response) {

                if (response.isSuccessful()) {
                    StoreDataObj storeDataObj = response.body();

                    if (storeDataObj.getAssignStorArr() != null) {

                        for(int i =0;i<storeDataObj.getAssignStorArr().size();i++)
                        {
                            setRecycler(storeDataObj.getAssignStorArr().get(i).getCheckLists());
                            setUi(storeDataObj.getAssignStorArr().get(i).getTaskName(),
                                    storeDataObj.getAssignStorArr().get(i).getTaskDate());
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Yo", Toast.LENGTH_SHORT).show();
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

    private void setUi(String taskname,String taskDate)
    {
        Toast.makeText(getActivity(), taskname, Toast.LENGTH_SHORT).show();
        tvTaskName.setText(taskname);
        tvTaskDate.setText(taskDate);
    }

    private void setRecycler(ArrayList<CheckList> arrayList)
    {
        adapter = new PendingCheckListAdapter(getActivity(),arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}