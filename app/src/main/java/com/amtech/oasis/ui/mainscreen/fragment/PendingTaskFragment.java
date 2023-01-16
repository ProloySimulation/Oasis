package com.amtech.oasis.ui.mainscreen.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
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
import com.amtech.oasis.model.StoreDataObj;
import com.amtech.oasis.model.Stores;
import com.amtech.oasis.model.Tasks;
import com.amtech.oasis.network.ApiClient;
import com.amtech.oasis.network.ApiInterface;
import com.amtech.oasis.ui.mainscreen.adapter.CompletedTaskAdapter;
import com.amtech.oasis.util.SharedPreferenceManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PendingTaskFragment extends Fragment {

    private RecyclerView recyclerView;
    private CompletedTaskAdapter adapter;
    private FusedLocationProviderClient client;
    private AppCompatImageView imvBack;
    private HashMap<String, String> headerMap = new HashMap<String, String>();
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_task, container, false);

        init(view);
        getAllAssignedTask();

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
        token = SharedPreferenceManager.getInstance(getActivity()).GetUserToken();
    }

    private void getAllAssignedTask()
    {
        headerMap.put("Authorization","Bearer "+token);
        ApiClient apiClient = new ApiClient();
        ApiInterface service = apiClient.createService(ApiInterface.class);
        Call<StoreDataObj> call = service.getAllTasks(headerMap);
        call.enqueue(new Callback<StoreDataObj>() {

            @Override
            public void onResponse(Call<StoreDataObj> call, Response<StoreDataObj> response) {

                if (response.isSuccessful()) {
                    StoreDataObj storeDataObj = response.body();

                    if (storeDataObj != null) {
                        setRecycler(storeDataObj.getAssignStorArr());
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

    private void setRecycler(List<AssignStorArr> arrayList)
    {
        List<Stores> assignStores = new ArrayList<>();
//        Toast.makeText(getActivity(), arrayList.get(0).getTaskName(), Toast.LENGTH_SHORT).show();;
        adapter = new CompletedTaskAdapter(getActivity(),"pending",assignStores,arrayList, new CompletedTaskAdapter.ClickListener() {
            @Override
            public void itemClickCompleted(int position) {
            }

            @Override
            public void itemClickPending(int position,String update) {
                if(update.equals("view"))
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("TASKID",arrayList.get(position).getTaskId());
                    bundle.putString("EDITABLE","NO");
                    Navigation.findNavController(requireView()).navigate(R.id.action_pendingTaskFragment_to_pendingDetailFragment,bundle);
                }
                else
                {
                    if(arrayList.get(position).getCheckLists().get(0).getStoreLat()!=null)
                    {
                        double storeLat = Double.parseDouble(arrayList.get(position).getCheckLists().get(0).getStoreLat());
                        double storeLong = Double.parseDouble(arrayList.get(position).getCheckLists().get(0).getStoreLat());
                        getmylocation(storeLat,storeLong,arrayList,position);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "No Lat & Long", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void itemClickAllAssign(int position) {

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

    public void getmylocation(double storeLat,double storeLong,List<AssignStorArr> arrayList,int position) {

        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        else
        {

            client = LocationServices.getFusedLocationProviderClient(requireActivity());
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {

                    if(location!=null)
                    {
//                        progressBar.setVisibility(View.GONE);
                        double marchandaiserLatitute = location.getLatitude();
                        double marchandaiserLongitute = location.getLongitude();
                        calculateLocationDifference(storeLat,storeLong,marchandaiserLatitute,marchandaiserLongitute,arrayList,position);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Location Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private float calculateLocationDifference(double storeLat,double storeLong,double marchandaiserLatitute,double marchandaiserLongitute,
                                              List<AssignStorArr> arrayList,int position) {
        float[] dist = new float[1];
        float maxDistance = 120.0f;
        Location.distanceBetween(storeLat,
                storeLong,
                marchandaiserLatitute,
                marchandaiserLongitute, dist);

        if(dist[0]<maxDistance)
        {
            Bundle bundle = new Bundle();
            bundle.putString("TASKID",arrayList.get(position).getTaskId());
            bundle.putString("EDITABLE","YES");
            Navigation.findNavController(requireView()).navigate(R.id.action_pendingTaskFragment_to_pendingDetailFragment,bundle);
        }

        else
        {
            Toast.makeText(requireActivity(), "You are not in the zone", Toast.LENGTH_SHORT).show();
        }

        return dist[0];
    }
}