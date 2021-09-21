package com.mbs.doctorapp.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mbs.doctorapp.R;
import com.mbs.doctorapp.model.entity.Patient;
import com.mbs.doctorapp.view.activity.OnePatientActivity;
import com.mbs.doctorapp.view.fragments.patients.PatientsFragment;
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
import io.paperdb.Paper;


public class DialogEditPatient extends DialogFragment
{
    Context context=getContext();


    private Patient currentPatient;
    private Spinner spinnerSex;
    private List<String> sex=new ArrayList<>();
    private ArrayAdapter adapterSex;
    private String spsex;
    private int id;
    private TextInputEditText edtDialogPatientEditName;
    private TextInputEditText edtDialogPatientEditAge;
    private TextInputEditText edtDialogPatientEditPhone;
    private TextInputEditText edtDialogPatientEditAddress;
    private TextInputEditText edtDialogPatientEditIllnesses;
    private TextInputEditText edtDialogPatientEditDrugs;
    private TextInputEditText edtDialogPatientEditReview;
    private TextInputEditText edtDialogPatientEditPayment;
    private Button btnDialogPatientEditSave;

    public DialogEditPatient( Patient patient) {
        super();
        currentPatient=patient;

    }
    private ViewModelPatient viewModelPatient;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        View view = layoutInflater.inflate(R.layout.dialog_edit_patient, null);

        edtDialogPatientEditName=view.findViewById(R.id.edtDialogPatientEditName);
        edtDialogPatientEditAge=view.findViewById(R.id.edtDialogPatientEditAge);
        edtDialogPatientEditPhone=view.findViewById(R.id.edtDialogPatientEditPhone);
        edtDialogPatientEditAddress=view.findViewById(R.id.edtDialogPatientEditAddress);
        edtDialogPatientEditIllnesses=view.findViewById(R.id.edtDialogPatientEditIllnesses);
        edtDialogPatientEditDrugs=view.findViewById(R.id.edtDialogPatientEditDrugs);
        edtDialogPatientEditReview=view.findViewById(R.id.edtDialogPatientEditReview);
        edtDialogPatientEditPayment=view.findViewById(R.id.edtDialogPatientEditPayment);
        btnDialogPatientEditSave=view.findViewById(R.id.btnDialogPatientEditSave);


        spinnerSex=view.findViewById(R.id.spnDialogPatientEditSex);
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

        edtDialogPatientEditName.setText(currentPatient.getName());
        edtDialogPatientEditAge.setText(currentPatient.getAge()+"");
        edtDialogPatientEditPhone.setText(currentPatient.getPhone());
        edtDialogPatientEditAddress.setText(currentPatient.getAddress());
        edtDialogPatientEditIllnesses.setText(currentPatient.getIllnesses());
        edtDialogPatientEditDrugs.setText(currentPatient.getDrugs());
        edtDialogPatientEditReview.setText(currentPatient.getReview());
        edtDialogPatientEditPayment.setText(currentPatient.getPayments()+"");
        sex.add(0,currentPatient.getSex());
        if(sex.get(0).equals("Male")){
            sex.add("Female");
        }
        else
        sex.add("Male");

        adapterSex=new ArrayAdapter(view.getContext(),android.R.layout.simple_spinner_dropdown_item,sex);
        adapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(adapterSex);

        viewModelPatient= ViewModelProviders.of(DialogEditPatient.this).get(ViewModelPatient.class);


        btnDialogPatientEditSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                int age,payment;

               currentPatient.setName(edtDialogPatientEditName.getText().toString());

                currentPatient.setSex(spsex);
                if(edtDialogPatientEditAge.getText().toString().equals(""))
                {
                    age=0;
                }
                else
                {
                     age = Integer.parseInt(edtDialogPatientEditAge.getText().toString());
                }
                currentPatient.setAge(age);


                if(edtDialogPatientEditPayment.getText().toString().equals(""))
                {
                    payment=0;
                }
                else
                {
                    payment = Integer.parseInt(edtDialogPatientEditPayment.getText().toString());
                }
                currentPatient.setPayments(payment);

                String phone=edtDialogPatientEditPhone.getText().toString();
                currentPatient.setPhone(phone);

                String address=edtDialogPatientEditAddress.getText().toString();
                currentPatient.setAddress(address);

                String illnesses=edtDialogPatientEditIllnesses.getText().toString();
                currentPatient.setIllnesses(illnesses);

                String drugs=edtDialogPatientEditDrugs.getText().toString();
currentPatient.setDrugs(drugs);
                String review=edtDialogPatientEditReview.getText().toString();
currentPatient.setReview(review);



                viewModelPatient.update(currentPatient);

                Paper.init(getContext());
                Paper.book().write("OnePatient", currentPatient);
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);
                dismiss();


            }
        });
        return view;
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
    }


