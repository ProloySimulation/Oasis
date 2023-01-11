package com.amtech.oasis.ui.mainscreen.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
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
import com.amtech.oasis.model.AssignStorArr;
import com.amtech.oasis.model.AssignStore;
import com.amtech.oasis.model.DashboardObject;
import com.amtech.oasis.model.StoreDataObj;
import com.amtech.oasis.model.Stores;
import com.amtech.oasis.model.Tasks;
import com.amtech.oasis.network.ApiClient;
import com.amtech.oasis.network.ApiInterface;
import com.amtech.oasis.ui.mainscreen.adapter.CompletedTaskAdapter;
import com.amtech.oasis.util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllAssignedFragment extends Fragment {

    private RecyclerView recyclerView;
    private CompletedTaskAdapter adapter;
    private AppCompatImageView imvBack;
    private HashMap<String, String> headerMap = new HashMap<String, String>();
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_assigned, container, false);

        init(view);
        getAssignedStores();

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerAllAssigned);
        imvBack = view.findViewById(R.id.imvBackAllAssigned);
        token = SharedPreferenceManager.getInstance(getActivity()).GetUserToken();
    }

    private void getAssignedStores()
    {
        headerMap.put("Authorization","Bearer "+token);
        ApiClient apiClient = new ApiClient();
        ApiInterface service = apiClient.createService(ApiInterface.class);
        Call<StoreDataObj> call = service.getAssignStores(headerMap);
        call.enqueue(new Callback<StoreDataObj>() {

            @Override
            public void onResponse(Call<StoreDataObj> call, Response<StoreDataObj> response) {

                if (response.isSuccessful()) {
                    StoreDataObj storeDataObj = response.body();

                    if (storeDataObj != null) {
                        for(int i =0;i<storeDataObj.getAssignStorArr().size();i++)
                        {
                            setRecycler(storeDataObj.getAssignStorArr().get(i).getStores());
                        }

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

    private void setRecycler(List<Stores> assignStores)
    {
        List<AssignStorArr> pendingTaskArrayList = new ArrayList<>();

        adapter = new CompletedTaskAdapter(getActivity(),"assigned",assignStores,pendingTaskArrayList, new CompletedTaskAdapter.ClickListener() {
            @Override
            public void itemClickCompleted(int position) {
            }

            @Override
            public void itemClickPending(int position,String update) {

            }

            @Override
            public void itemClickAllAssign(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("STOREID",assignStores.get(position).getStoreId());
                Navigation.findNavController(requireView()).navigate(R.id.action_allAssignedFragment_to_assignedDetailFragment,bundle);

            }

            @Override
            public void showDialog(String imageUrl) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}