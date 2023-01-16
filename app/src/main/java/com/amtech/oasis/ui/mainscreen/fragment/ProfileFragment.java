package com.amtech.oasis.ui.mainscreen.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amtech.oasis.MainActivity;
import com.amtech.oasis.R;
import com.amtech.oasis.model.Login;
import com.amtech.oasis.model.Profile;
import com.amtech.oasis.model.ProfileObject;
import com.amtech.oasis.model.Tasks;
import com.amtech.oasis.network.ApiClient;
import com.amtech.oasis.network.ApiInterface;
import com.amtech.oasis.ui.mainscreen.activity.ActivityLogin;
import com.amtech.oasis.ui.mainscreen.activity.ActivityResetPassword;
import com.amtech.oasis.util.PermissionsUtil;
import com.amtech.oasis.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    private AppCompatEditText etName,etEmail;
    private AppCompatButton btnUpdate;
    private AppCompatImageView imvBack;
    private CircleImageView imvProfile;
    private ProgressBar progressBar;
    private AppCompatTextView tvChangePassword;
    private HashMap<String, String> headerMap = new HashMap<String, String>();
    private String token,userId;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 101;
    private final int GALLERY_REQUEST_CODE = 102;
    private String currentPhotoPath;
    private Bitmap bitmap;
    private File image = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        init(view);
        getProfile();

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityResetPassword.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(image!=null)
                {
                    profileUpdateImage();
                }
                else
                {
                    updateProfile();
                }
            }
        });

        imvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        return view;
    }

    private void init(View view) {
        imvBack = view.findViewById(R.id.imvBackProfile);
        imvProfile = view.findViewById(R.id.profileHeaderProfilePicture);
        tvChangePassword = view.findViewById(R.id.tvProfileChangePassword);
        progressBar =  view.findViewById(R.id.progressBarProfile);
        btnUpdate = view.findViewById(R.id.btnProfileUpdate);
        etName = view.findViewById(R.id.tvProfileName);
        etEmail = view.findViewById(R.id.etProfileUserEmail);
//        etAssignedName = view.findViewById(R.id.etAssignedName);

        token = SharedPreferenceManager.getInstance(getActivity()).GetUserToken();

        etEmail.setEnabled(false);
//        etAssignedName.setEnabled(false);
    }

    private void getProfile()
    {
        progressBar.setVisibility(View.VISIBLE);
        headerMap.put("Authorization","Bearer "+token);
        ApiClient apiClient = new ApiClient();
        ApiInterface service = apiClient.createService(ApiInterface.class);
        Call<ProfileObject> call = service.getProfile(headerMap);
        call.enqueue(new Callback<ProfileObject>() {

            @Override
            public void onResponse(Call<ProfileObject> call, Response<ProfileObject> response) {

                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    ProfileObject loginResponse = response.body();

                    if (loginResponse != null) {
                        userId = loginResponse.getProfile().getUserId();

                        etName.setText(loginResponse.getProfile().getName());
                        etEmail.setText(loginResponse.getProfile().getEmail());
//                        etAssignedName.setText(loginResponse.getProfile().getAssignedName());
                        Picasso.with(getActivity()).load(loginResponse.getProfile().getProfileImage()).into(imvProfile);
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfileObject> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("ListSize", " - > Error    " + t.getMessage());
            }
        });
    }

    private void updateProfile()
    {
        progressBar.setVisibility(View.VISIBLE);
        String userName = etName.getText().toString();
        Profile profile = new Profile(userId,userName);

        headerMap.put("Authorization","Bearer "+token);
        ApiClient apiClient = new ApiClient();
        ApiInterface service = apiClient.createService(ApiInterface.class);
        Call<Profile> call = service.profileUpdate(headerMap,profile);
        call.enqueue(new Callback<Profile>() {

            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {

                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Profile loginResponse = response.body();

                    if (loginResponse != null) {
                        Toast.makeText(getActivity(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                Log.d("ListSize", " - > Error    " + t.getMessage());
            }
        });
    }

    private void profileUpdateImage()
    {
        progressBar.setVisibility(View.VISIBLE);
        String name = etName.getText().toString();
        RequestBody updateUserId = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody updateUserName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody uploadImage = RequestBody.create(MediaType.parse("image/*"), image);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("profile_image", image.getName(), uploadImage);

        headerMap.put("Authorization","Bearer "+token);
        ApiClient apiClient = new ApiClient();
        ApiInterface service = apiClient.createService(ApiInterface.class);
        Call<Profile> call = service.profileUpdateImage(headerMap,updateUserId,updateUserName,parts);
        call.enqueue(new Callback<Profile>() {

            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {

                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Profile profileObj = response.body();
                    Toast.makeText(getActivity(), profileObj.getMessage(), Toast.LENGTH_SHORT).show();
                }

                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "User Id Is Logged In Another Device", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Log.d("ListSize", " - > Error    " + t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(uri, filePath, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePath[0]);
                String myPath = cursor.getString(columnIndex);
                cursor.close();

                //added part start

                float abs_width = 0.0f;
                float abs_height = 0.0f;

                if (abs_height > 6000 || abs_width > 5000) {
                    Toast.makeText(getActivity(), "Please choose Passport Size Photo!", Toast.LENGTH_LONG).show();
                    return;
                }

                currentPhotoPath = myPath;
                image = new File(currentPhotoPath);
                imvProfile.setImageURI(Uri.fromFile(image));

                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
//                    encodeBitmapImage(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void openGallery() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionExplanation();
            } else if (!PermissionsUtil.getInstance(getActivity()).checkReadExternalStoragePermissionPreference()) {
                PermissionsUtil.getInstance(getActivity()).updateReadExternalStoragePermissionPreference();
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
            } else {
                Toast.makeText(getActivity(), "Please Allow Read External Storage Permission", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                this.startActivity(intent);
            }
        } else {
            takeImageFromGallery();
        }
    }

    private void takeImageFromGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_REQUEST_CODE);
    }

    private void showPermissionExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Read External Storage Permission Needed");
        builder.setMessage("Oasis needs to access your Gallery to pick a photo of yours. So please give permission to Read External Storage.");
        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}