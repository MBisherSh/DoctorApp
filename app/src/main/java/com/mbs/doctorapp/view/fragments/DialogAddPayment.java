package com.mbs.doctorapp.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.mbs.doctorapp.R;
import com.mbs.doctorapp.model.entity.Payment;
import com.mbs.doctorapp.model.entity.Patient;
import com.mbs.doctorapp.view.adapter.AdapterSpinnerPatients;
import com.mbs.doctorapp.viewmodel.ViewModelPayment;
import com.mbs.doctorapp.viewmodel.ViewModelPatient;

import java.util.ArrayList;
import java.util.List;


public class DialogAddPayment extends DialogFragment {
    Context context = getContext();

    private TextInputEditText edtDialogPaymentAddOperation;
    private TextInputEditText edtDialogPaymentAddValue;
    private Spinner spnDialogPaymentAddName;
    private List<String> patientsNames;
    private List<Patient> patients;

    private DatePicker dpDialogPaymentAddDate;
    private TimePicker dpDialogPaymentAddTime;

    private Button btnDialogPaymentAddSave;

    private Patient patient;

    private ViewModelPayment viewModelPayment;
    private ViewModelPatient viewModelPatient;
    private AdapterSpinnerPatients adapterPatient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModelPatient = ViewModelProviders.of(DialogAddPayment.this).get(ViewModelPatient.class);
        viewModelPayment = ViewModelProviders.of(DialogAddPayment.this).get(ViewModelPayment.class);

        final View view = layoutInflater.inflate(R.layout.dialog_add_payment, null);

        edtDialogPaymentAddOperation = view.findViewById(R.id.edtDialogPaymentAddOperation);
        edtDialogPaymentAddValue=view.findViewById(R.id.edtDialogPaymentAddValue);
        spnDialogPaymentAddName = view.findViewById(R.id.spnDialogPaymentAddName);
        dpDialogPaymentAddDate = view.findViewById(R.id.dpDialogPaymentAddDate);
        dpDialogPaymentAddTime = view.findViewById(R.id.dpDialogPaymentAddTime);
        btnDialogPaymentAddSave = view.findViewById(R.id.btnDialogPaymentAddSave);

        patients = new ArrayList<>();

        viewModelPatient.getAll().observe(getViewLifecycleOwner(), new Observer<List<Patient>>() {
            @Override
            public void onChanged(List<Patient> patientss) {
                patients = patientss;

                final AdapterSpinnerPatients adapterPatient = new AdapterSpinnerPatients(view.getContext(),
                        android.R.layout.simple_spinner_item, patients);
                adapterPatient.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnDialogPaymentAddName.setAdapter(adapterPatient);
                spnDialogPaymentAddName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        patient = adapterPatient.getItem(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


        btnDialogPaymentAddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtDialogPaymentAddValue.getText().toString().equals(""))
                    Toast.makeText(getContext(), "Please Enter Payment value !", Toast.LENGTH_SHORT).show();
                else {
                    String operation = edtDialogPaymentAddOperation.getText().toString();

                    int value = Integer.parseInt(edtDialogPaymentAddValue.getText().toString());
                    int patientId = patient.getId();

                    int month=(dpDialogPaymentAddDate.getMonth() + 1);
                    String name = patient.getName();

                    String date = "" + dpDialogPaymentAddDate.getYear()
                                     +(month  <10 ?"0" +month : month)
                                     +(dpDialogPaymentAddDate.getDayOfMonth()   <10 ?"0" + dpDialogPaymentAddDate.getDayOfMonth() : dpDialogPaymentAddDate.getDayOfMonth())
                                     +(dpDialogPaymentAddTime.getHour()         <10 ?"0" + dpDialogPaymentAddTime.getHour()       : dpDialogPaymentAddTime.getHour())
                                     +(dpDialogPaymentAddTime.getMinute()       <10 ?"0" + dpDialogPaymentAddTime.getMinute()     : dpDialogPaymentAddTime.getMinute());

                    Payment payment = new Payment(name, operation, date, value, patientId);

                    viewModelPayment.insert(payment);
                    patient.setPayments(patient.getPayments()-value);
                    viewModelPatient.update(patient);
                    dismiss();

                }
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
/*  dpDialogPaymentAddTime.getHour()+(dpDialogPaymentAddTime.getMinute() < 10 ? "0" + dpDialogPaymentAddTime.getMinute() : dpDialogPaymentAddTime.getMinute())
                         + dpDialogPaymentAddDate.getDayOfMonth() + (dpDialogPaymentAddDate.getMonth() + 1) +
                        dpDialogPaymentAddDate.getYear();*/