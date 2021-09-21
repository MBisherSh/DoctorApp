package com.mbs.doctorapp.view.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mbs.doctorapp.R;
import com.mbs.doctorapp.model.entity.Patient;
import com.mbs.doctorapp.viewmodel.ViewModelPatient;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;


public class DialogAddPatient extends DialogFragment
{
    Context context=getContext();


    private Spinner spinnerSex;
    private List<String> sex;
    private ArrayAdapter adapterSex;
    private String spsex;

    private static final int CAMERA_REQUEST = 1888;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private ImageView imgDialogPatientAddImage;
    private FloatingActionButton fabDialogPatientAddCam;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RESULT_OK = -1;
    private final int PER_REQUEST_EXTERNAL = 1000;
    private final int REQUEST_GALLERY = 100;
    private TextInputEditText edtDialogPatientAddName;
    private TextInputEditText edtDialogPatientAddAge;
    private TextInputEditText edtDialogPatientAddPhone;
    private TextInputEditText edtDialogPatientAddAddress;
    private TextInputEditText edtDialogPatientAddIllnesses;
    private TextInputEditText edtDialogPatientAddDrugs;
    private TextInputEditText edtDialogPatientAddReview;
    private TextInputEditText edtDialogPatientAddPayment;
    private Button btnDialogPatientAddSave;

    private ViewModelPatient viewModelPatient;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = layoutInflater.inflate(R.layout.dialog_add_patient, null);

        imgDialogPatientAddImage=view.findViewById(R.id.imgDialogPatientAddImage);
        fabDialogPatientAddCam=view.findViewById(R.id.fabDialogPatientAddCam);
        edtDialogPatientAddName=view.findViewById(R.id.edtDialogPatientAddName);
        edtDialogPatientAddAge=view.findViewById(R.id.edtDialogPatientAddAge);
        edtDialogPatientAddPhone=view.findViewById(R.id.edtDialogPatientAddPhone);
        edtDialogPatientAddAddress=view.findViewById(R.id.edtDialogPatientAddAddress);
        edtDialogPatientAddIllnesses=view.findViewById(R.id.edtDialogPatientAddIllnesses);
        edtDialogPatientAddDrugs=view.findViewById(R.id.edtDialogPatientAddDrugs);
        edtDialogPatientAddReview=view.findViewById(R.id.edtDialogPatientAddReview);
        edtDialogPatientAddPayment=view.findViewById(R.id.edtDialogPatientAddPayment);
        btnDialogPatientAddSave=view.findViewById(R.id.btnDialogPatientAddSave);


        spinnerSex=view.findViewById(R.id.spnDialogPatientAddSex);
        spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String s=sex.get(position);
                spsex=s;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sex=new ArrayList<>();
        sex.add("Male");
        sex.add("Female");
        adapterSex=new ArrayAdapter(view.getContext(),android.R.layout.simple_spinner_dropdown_item,sex);
        adapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(adapterSex);

        fabDialogPatientAddCam.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1)
                        .start(getContext(),DialogAddPatient.this);

                // Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                   // startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });

        viewModelPatient= ViewModelProviders.of(DialogAddPatient.this).get(ViewModelPatient.class);

        btnDialogPatientAddSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                byte[] image;
                int age;
                int payment;
                if (imgDialogPatientAddImage.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.ic_patient).getConstantState())
                {
                 //   image = (byte[]) getResources().getDrawable( R.drawable.ic_person)
                    image=null;
                }
                else
                {
                    image = imageViewToByteArray(imgDialogPatientAddImage);
                }



                String name=edtDialogPatientAddName.getText().toString();

                String sex=spsex;

                if(edtDialogPatientAddAge.getText().toString().equals(""))
                {
                    age=0;
                }
                else
                {
                     age = Integer.parseInt(edtDialogPatientAddAge.getText().toString());
                }
                String phone=edtDialogPatientAddPhone.getText().toString();

                String Address=edtDialogPatientAddAddress.getText().toString();

                String illnesses=edtDialogPatientAddIllnesses.getText().toString();

                String drugs=edtDialogPatientAddDrugs.getText().toString();

                String review=edtDialogPatientAddReview.getText().toString();

                if(edtDialogPatientAddPayment.getText().toString().equals(""))
                {
                    payment=0;
                }
                else
                {
                    payment=Integer.parseInt(edtDialogPatientAddPayment.getText().toString());
                }

                Patient patient=new Patient(name,sex, Address,phone,illnesses,drugs,review,age,image,payment);

                viewModelPatient.insert(patient);

                dismiss();



            }
        });
        return view;
    }

    private byte[] imageViewToByteArray(ImageView imageView)
    {
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private byte[] bitmapToByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri croppedImageUri = result.getUri();
                File imageFile = new File(croppedImageUri.getPath());
                try
                {
                    Bitmap bitmap = new Compressor(getContext())
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(100)
                            .compressToBitmap(imageFile);

                    //imgBottomSheetCategoryImage.setImageBitmap(bitmap);

                    Glide.with(this).load(bitmap).into(imgDialogPatientAddImage);
                }
                catch (IOException ex)
                {
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
            }
        }
    }

    private void cropImage(Uri imageUri)
    {
        CropImage.activity(imageUri)
                .setAspectRatio(1, 1)
                .setMinCropWindowSize(800, 800)
                .start(getActivity());
    }

    public Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }
}
