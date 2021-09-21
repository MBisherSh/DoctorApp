package com.mbs.doctorapp.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mbs.doctorapp.R;
import com.mbs.doctorapp.model.entity.Appointment;
import com.mbs.doctorapp.model.entity.Patient;

import com.mbs.doctorapp.model.entity.Payment;
import com.mbs.doctorapp.view.adapter.AdapterRecyclerAppointment;
import com.mbs.doctorapp.view.adapter.AdapterRecyclerOnePatientAppointments;
import com.mbs.doctorapp.view.adapter.AdapterRecyclerOnePatientPayments;
import com.mbs.doctorapp.view.adapter.AdapterRecyclerPayment;
import com.mbs.doctorapp.view.fragments.DialogAddAmount;
import com.mbs.doctorapp.view.fragments.DialogEditPatient;
import com.mbs.doctorapp.view.fragments.appointments.AppointmentsFragment;
import com.mbs.doctorapp.view.fragments.patients.PatientsFragment;
import com.mbs.doctorapp.view.fragments.payments.PaymentsFragment;
import com.mbs.doctorapp.viewmodel.ViewModelAppointment;
import com.mbs.doctorapp.viewmodel.ViewModelPatient;
import com.mbs.doctorapp.viewmodel.ViewModelPayment;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.paperdb.Paper;

import static android.os.Build.VERSION_CODES.P;

public class OnePatientActivity extends AppCompatActivity
{
    private Patient currentPatient;
    private int total;
    private ImageView imgOnePatientImage;
    private TextView txtOnePatientName;

    private FloatingActionButton fabOnePatientEdit;
    private FloatingActionButton fabOnePatientEditImage;
    private FloatingActionButton fabOnePatientDelete;
    private FloatingActionButton fabOnePatientAddAmount;
    private TextView txtOnePatientAge;
    private TextView txtOnePatientGender;
    private TextView txtOnePatientPhone;
    private TextView txtOnePatientAddress;
    private TextView txtOnePatientID;
    private TextView txtOnePatientDrugs;
    private TextView txtOnePatientReview;
    private TextView txtOnePatientIllnesses;
    private TextView txtOnePatientPayment;
    private TextView txtOnePatientTotal;
    private ViewModelPatient viewModelPatient;

    private ViewModelPayment viewModelPayment;
    private RecyclerView recyclerOnePatientPayments;
    private RecyclerView.LayoutManager layoutManager;
    private List<Payment> payments;
    private AdapterRecyclerOnePatientPayments adapterRecyclerPayment;

    private ViewModelAppointment viewModelAppointment;

    private RecyclerView recyclerOnePatientAppointments;
    private RecyclerView.LayoutManager layoutManagerAppointments;
    private List<Appointment> appointments;
    private AdapterRecyclerOnePatientAppointments adapterRecyclerAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_patient);


        Paper.init(this);
        currentPatient = Paper.book().read("OnePatient");
        getSupportActionBar().setTitle(currentPatient.getName());

        imgOnePatientImage=findViewById(R.id.imgOnePatientImage);
        txtOnePatientName=findViewById(R.id.txtOnePatientName);
        fabOnePatientEdit=findViewById(R.id.fabOnePatientEdit);
        fabOnePatientEditImage=findViewById(R.id.fabOnePatientEditImage);
        fabOnePatientDelete=findViewById(R.id.fabOnePatientDelete);
        fabOnePatientAddAmount=findViewById(R.id.fabOnePatientAddAmount);
        txtOnePatientGender=findViewById(R.id.txtOnePatientSex);
        txtOnePatientAge=findViewById(R.id.txtOnePatientAge);
        txtOnePatientPhone=findViewById(R.id.txtOnePatientPhone);
        txtOnePatientAddress=findViewById(R.id.txtOnePatientAddress);
        txtOnePatientDrugs=findViewById(R.id.txtOnePatientDrugs);
        txtOnePatientReview=findViewById(R.id.txtOnePatientReview);
        txtOnePatientID=findViewById(R.id.txtOnePatientID);
        txtOnePatientIllnesses=findViewById(R.id.txtOnePatientIllnesses);
        txtOnePatientPayment=findViewById(R.id.txtOnePatientPayment);
        txtOnePatientTotal=findViewById(R.id.txtOnePatientTotal);

        Glide.with(this).load(currentPatient.getImage()).into(imgOnePatientImage);
        txtOnePatientPayment.setText(currentPatient.getPayments()+"");
        txtOnePatientName.setText(currentPatient.getName());
        txtOnePatientGender.setText(currentPatient.getSex());
        txtOnePatientAge.setText(currentPatient.getAge()+"");
        txtOnePatientPhone.setText(currentPatient.getPhone());
        txtOnePatientAddress.setText(currentPatient.getAddress());
        txtOnePatientDrugs.setText(currentPatient.getDrugs());
        txtOnePatientReview.setText(currentPatient.getReview());
        txtOnePatientID.setText(currentPatient.getId()+"");
        txtOnePatientGender.setText(currentPatient.getSex());
        txtOnePatientIllnesses.setText(currentPatient.getIllnesses());
        viewModelPatient= ViewModelProviders.of(this).get(ViewModelPatient.class);

        recyclerOnePatientPayments= findViewById(R.id.recyclerOnePatientPayments);
        layoutManager=new LinearLayoutManager(this);
        recyclerOnePatientPayments.setLayoutManager(layoutManager);
        payments=new ArrayList<>();
        adapterRecyclerPayment=new AdapterRecyclerOnePatientPayments(this,payments);
        recyclerOnePatientPayments.setAdapter(adapterRecyclerPayment);
        viewModelPayment=ViewModelProviders.of(this).get(ViewModelPayment.class);

        viewModelAppointment= ViewModelProviders.of(this).get(ViewModelAppointment.class);

        recyclerOnePatientAppointments= (RecyclerView) findViewById(R.id.recyclerOnePatientAppointments);
        layoutManagerAppointments=new LinearLayoutManager(this);
        recyclerOnePatientAppointments.setLayoutManager(layoutManagerAppointments);
        appointments=new ArrayList<>();
        adapterRecyclerAppointment=new AdapterRecyclerOnePatientAppointments(this,appointments);
        recyclerOnePatientAppointments.setAdapter(adapterRecyclerAppointment);
        viewModelAppointment.getAllByPatient(currentPatient.getId()).observe(this, new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments)
            {
                OnePatientActivity.this.appointments=appointments;
                if(appointments.size()==0)
                    recyclerOnePatientAppointments.setLayoutParams(new LinearLayout.LayoutParams(-1,-2));
                adapterRecyclerAppointment.setData(appointments);
            }
        });


        fabOnePatientEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogEditPatient dialogEditPatient=new DialogEditPatient(currentPatient);
                dialogEditPatient.show(getSupportFragmentManager(), "DialogEditPatient");
            }
        });

        fabOnePatientEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1)
                        .start(OnePatientActivity.this);
            }
        });

        fabOnePatientDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(OnePatientActivity.this)
                        .setTitle("Delete Patient")
                        .setMessage("Do you really want to Delete this Patient?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                viewModelPatient.delete(currentPatient);
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

            }
        });

        fabOnePatientAddAmount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogAddAmount dialogAddAmount=new DialogAddAmount(currentPatient);
                dialogAddAmount.show(getSupportFragmentManager(), "DialogAddAmount");
            }
        });

        viewModelPayment.getAllByID(currentPatient.getId()).observe(this, new Observer<List<Payment>>() {
            @Override
            public void onChanged(List<Payment> payments)
            {

                OnePatientActivity.this.payments=payments;
                if(payments.size()==0)
                    recyclerOnePatientPayments.setLayoutParams(new LinearLayout.LayoutParams(-1,-2));
                adapterRecyclerPayment.setData(payments);
                total=0;
                for (Payment p:payments)
                {
                    total+=p.getValue();

                }
                txtOnePatientTotal.setText(total+"");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri croppedImageUri = result.getUri();
                File imageFile = new File(croppedImageUri.getPath());
                try {
                    Bitmap bitmap = new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(100)
                            .compressToBitmap(imageFile);

                    currentPatient.setImage(bitmapToByteArray(bitmap));
                    viewModelPatient.update(currentPatient);
                    Glide.with(this).load(bitmap).into(imgOnePatientImage);
                } catch (IOException ex) {
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    private byte[] bitmapToByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public void deletePayment(Payment payment){
        viewModelPayment.delete(payment);
    }
    public void deleteAppointment(Appointment appointment){
        viewModelAppointment.delete(appointment);
    }
}
