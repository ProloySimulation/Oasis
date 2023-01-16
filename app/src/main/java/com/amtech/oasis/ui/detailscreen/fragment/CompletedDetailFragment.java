package com.amtech.oasis.ui.detailscreen.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amtech.oasis.R;
import com.amtech.oasis.model.CheckList;
import com.amtech.oasis.model.StoreDataObj;
import com.amtech.oasis.network.ApiClient;
import com.amtech.oasis.network.ApiInterface;
import com.amtech.oasis.ui.detailscreen.adapter.PendingCheckListAdapter;
import com.amtech.oasis.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CompletedDetailFragment extends Fragment {

    private AppCompatTextView tvTaskName,tvTaskDate;
    private CardView cardCaptureImage;
    private PendingCheckListAdapter adapter;
    private AppCompatImageView imvBack;
    private AppCompatButton btnBack;
    private String token,taskId;
    private RecyclerView recyclerView;
    private LinearLayoutCompat layoutCheckList,layoutCaptureImage;
    private HashMap<String, String> headerMap = new HashMap<String, String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail, container, false);

        init(view);
        getCompletedTask();

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

    private void getCompletedTask()
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
//                            setRecycler(storeDataObj.getAssignStorArr().get(i).getCheckLists());
                            setUi(storeDataObj.getAssignStorArr().get(i).getTaskName(),
                                    storeDataObj.getAssignStorArr().get(i).getTaskDate());

                            for(int j =0;j<storeDataObj.getAssignStorArr().get(i).getCheckLists().size();j++)
                            {
                                LinearLayout parent = new LinearLayout(getActivity());
                                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                parent.setOrientation(LinearLayout.HORIZONTAL);

                                AppCompatTextView txtName = new AppCompatTextView(getActivity());
                                txtName.setEllipsize(TextUtils.TruncateAt.END);
                                txtName.setMaxLines(2);
                                txtName.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                                txtName.setText(storeDataObj.getAssignStorArr().get(i).getCheckLists().get(j).getCheckListName());
                                layoutCheckList.addView(txtName);
                                layoutCheckList.addView(parent);

                                if(storeDataObj.getAssignStorArr().get(i).getCheckLists().get(j).getImageCheck().equals("0"))
                                {
                                    AppCompatTextView txtYes = new AppCompatTextView(getActivity());
                                    txtYes.setTextSize(12);
                                    txtYes.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                                    AppCompatTextView txtNo = new AppCompatTextView(getActivity());
                                    txtNo.setTextSize(12);
                                    txtNo.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                                    AppCompatImageView imvYes = new AppCompatImageView(getActivity());
                                    AppCompatImageView imvNo = new AppCompatImageView(getActivity());

                                    txtYes.setText("Yes");
                                    txtNo.setText("No");
                                    imvYes.setImageResource(R.drawable.icon_yes);
                                    imvNo.setImageResource(R.drawable.icon_no);

                                    if(storeDataObj.getAssignStorArr().get(i).getCheckLists().get(j).getImage().equals("yes"))
                                    {
                                        parent.addView(imvYes);
                                        parent.addView(txtYes);
                                        parent.addView(imvNo);
                                        parent.addView(txtNo);
                                    }
                                    else
                                    {
                                        parent.addView(imvNo);
                                        parent.addView(txtYes);
                                        parent.addView(imvYes);
                                        parent.addView(txtNo);
                                    }
                                }
                                else
                                {
                                    cardCaptureImage.setVisibility(View.VISIBLE);
                                    AppCompatImageView imvCapture = new AppCompatImageView(getActivity());
                                    imvCapture.setLayoutParams(
                                            new ViewGroup.LayoutParams(
                                                    // or ViewGroup.LayoutParams.WRAP_CONTENT
                                                    150,
                                                    // or ViewGroup.LayoutParams.WRAP_CONTENT,
                                                    200 ) );
                                    Picasso.with(getActivity()).load(storeDataObj.getAssignStorArr().get(i).getCheckLists().get(j).getImage()).into(imvCapture);
                                    layoutCaptureImage.addView(imvCapture);
                                }
                            }
                        }
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

    /*private void setRecycler(ArrayList<CheckList> arrayList)
    {
        adapter = new PendingCheckListAdapter(getActivity(), arrayList, new PendingCheckListAdapter.ClickListener() {
            @Override
            public void itemClick(int position) {
                checkId = arrayList.get(position).getCheckId();
                if(arrayList.get(position).getImageCheck().equals("1"))
                {
                    cardView.setVisibility(View.VISIBLE);
                }
                else
                {
                    cardView.setVisibility(View.GONE);
                }
            }

            @Override
            public void checkList(int position,String checkAns) {
                checkIdAnswer = checkAns;
                checkId = arrayList.get(position).getCheckId();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }*/

    private void setUi(String taskname,String taskDate)
    {
        tvTaskName.setText(taskname);
        tvTaskDate.setText(taskDate);
    }

    private void init(View view) {
        tvTaskName = view.findViewById(R.id.tvCompletedDetailTaskName);
        tvTaskDate = view.findViewById(R.id.tvCompletedDetailTaskDate);
        layoutCaptureImage = view.findViewById(R.id.layoutCaptureImage);
        cardCaptureImage = view.findViewById(R.id.cardCompletedDetailImage);
        imvBack = view.findViewById(R.id.imvBackCompletedTaskDetail);
        btnBack = view.findViewById(R.id.btnCompletedBack);
//        recyclerView = view.findViewById(R.id.rvCompletedDetailTaskCheck);
        layoutCheckList = view.findViewById(R.id.layoutCompletedDetailCheckList);
        taskId = getArguments().getString("TASKID");
        token = SharedPreferenceManager.getInstance(getActivity()).GetUserToken();
    }
}