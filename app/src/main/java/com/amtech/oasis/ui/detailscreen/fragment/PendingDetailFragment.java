package com.amtech.oasis.ui.detailscreen.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PendingDetailFragment extends Fragment {

    private AppCompatImageView imvBack,imvCapture;
    private AppCompatTextView tvTaskName,tvTaskDate;
    private CardView cardView;
    private AppCompatButton btnUpdate;
    private RecyclerView recyclerView;
    private String taskId,token,checkId,checkIdAnswer;
    private PendingCheckListAdapter adapter;
    private File uploadImage = null;
    private ProgressBar progressBar;
    private HashMap<String, String> headerMap = new HashMap<String, String>();

    String currentPhotoPath;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pending_detail, container, false);

        init(view);

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        imvCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermissions();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadImage !=null)
                {
                    taskUpdateImage();
                }
                else
                {
                    taskUpdateData();
                }
            }
        });

        return view;
    }

    private void init(View view)
    {
        imvBack = view.findViewById(R.id.imvBackPendingDetail);
        imvCapture = view.findViewById(R.id.imvUploadImage);
        tvTaskName = view.findViewById(R.id.tvPendingTaskDetailName);
        tvTaskDate = view.findViewById(R.id.tvPendingTaskDetailDate);
        cardView = view.findViewById(R.id.carViewImageUpload);
        btnUpdate = view.findViewById(R.id.btnPendingUpdate);
        recyclerView = view.findViewById(R.id.recyclerPendingCheckList);
        progressBar = view.findViewById(R.id.progressBarPendingDetail);
        taskId = getArguments().getString("TASKID");
        String editable = getArguments().getString("EDITABLE");
        token = SharedPreferenceManager.getInstance(getActivity()).GetUserToken();

        if(editable.equals("NO"))
        {
            btnUpdate.setVisibility(View.GONE);
            getPendingTask("NO");
        }
    }

    private void getPendingTask(String editable)
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
                            if(editable.equals("NO"))
                            {
                                setRecycler(storeDataObj.getAssignStorArr().get(i).getCheckLists(),true);
                            }
                            else
                            {
                                setRecycler(storeDataObj.getAssignStorArr().get(i).getCheckLists(),false);
                            }
                            setUi(storeDataObj.getAssignStorArr().get(i).getTaskName(),
                                    storeDataObj.getAssignStorArr().get(i).getTaskDate());
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

    private void taskUpdateData()
    {
        if(!checkIdAnswer.isEmpty())
        {
            progressBar.setVisibility(View.VISIBLE);
            RequestBody checkIdNumber = RequestBody.create(MediaType.parse("text/plain"), checkId);
            RequestBody pendingTaskId = RequestBody.create(MediaType.parse("text/plain"), taskId);
            RequestBody checkAnswer = RequestBody.create(MediaType.parse("text/plain"), checkIdAnswer);

            headerMap.put("Authorization","Bearer "+token);
            ApiClient apiClient = new ApiClient();
            ApiInterface service = apiClient.createService(ApiInterface.class);
            Call<Tasks> call = service.taskUpdate(headerMap,checkIdNumber,pendingTaskId,checkAnswer);
            call.enqueue(new Callback<Tasks>() {

                @Override
                public void onResponse(Call<Tasks> call, Response<Tasks> response) {

                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        Tasks storeDataObj = response.body();
                        Toast.makeText(getActivity(), storeDataObj.getMessage(), Toast.LENGTH_SHORT).show();
                        imvCapture.setImageResource(R.drawable.ic_camera);
                    }

                    else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "User Id Is Logged In Another Device", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Tasks> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Log.d("ListSize", " - > Error    " + t.getMessage());
                }
            });
        }
        else
        {
            Toast.makeText(requireActivity(), "Please Select An Answer", Toast.LENGTH_SHORT).show();
        }

    }

    private void taskUpdateImage()
    {
        if(uploadImage!=null)
        {
            progressBar.setVisibility(View.VISIBLE);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), uploadImage);
            MultipartBody.Part parts = MultipartBody.Part.createFormData("image", uploadImage.getName(), requestBody);
            RequestBody checkIdNumber = RequestBody.create(MediaType.parse("text/plain"), checkId);
            RequestBody pendingTaskId = RequestBody.create(MediaType.parse("text/plain"), taskId);

            headerMap.put("Authorization","Bearer "+token);
            ApiClient apiClient = new ApiClient();
            ApiInterface service = apiClient.createService(ApiInterface.class);
            Call<Tasks> call = service.taskUpdateImage(headerMap,checkIdNumber,pendingTaskId,parts);
            call.enqueue(new Callback<Tasks>() {

                @Override
                public void onResponse(Call<Tasks> call, Response<Tasks> response) {

                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        Tasks storeDataObj = response.body();
                        Toast.makeText(getActivity(), storeDataObj.getMessage(), Toast.LENGTH_SHORT).show();
                        uploadImage = null;
                    }

                    else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "User Id Is Logged In Another Device", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Tasks> call, Throwable t) {

                    progressBar.setVisibility(View.GONE);
                    Log.d("ListSize", " - > Error    " + t.getMessage());
                }
            });
        }
        else
        {
            Toast.makeText(requireActivity(), "Please Select An Image", Toast.LENGTH_SHORT).show();
        }

    }

    private void setUi(String taskname,String taskDate)
    {
        tvTaskName.setText(taskname);
        tvTaskDate.setText(taskDate);
    }

    private void setRecycler(ArrayList<CheckList> arrayList,boolean viewable)
    {
        adapter = new PendingCheckListAdapter(getActivity(), arrayList,viewable, new PendingCheckListAdapter.ClickListener() {
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
    }


    //Camera Functionalities

    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(requireActivity(),
                        "com.amtech.oasis.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                uploadImage = new File(currentPhotoPath);
                imvCapture.setImageURI(Uri.fromFile(uploadImage));
                Log.d("tag", "ABsolute Url of Image is " + Uri.fromFile(uploadImage));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(uploadImage);
                mediaScanIntent.setData(contentUri);
                requireActivity().sendBroadcast(mediaScanIntent);
            }
        }
    }
}