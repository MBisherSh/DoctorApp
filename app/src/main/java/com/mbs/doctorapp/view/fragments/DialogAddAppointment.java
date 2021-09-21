package com.mbs.doctorapp.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.mbs.doctorapp.R;
import com.mbs.doctorapp.model.entity.Appointment;
import com.mbs.doctorapp.model.entity.Patient;
import com.mbs.doctorapp.view.adapter.AdapterSpinnerPatients;
import com.mbs.doctorapp.viewmodel.ViewModelAppointment;
import com.mbs.doctorapp.viewmodel.ViewModelPatient;

import java.util.ArrayList;
import java.util.List;


public class DialogAddAppointment extends DialogFragment {
    Context context = getContext();

    private TextInputEditText edtDialogAppointmentAddOperation;

    private Spinner spnDialogAppointmentAddName;
    private List<String> patientsNames;
    private List<Patient> patients;

    private DatePicker dpDialogAppointmentAddDate;
    private TimePicker dpDialogAppointmentAddTime;

    private Button btnDialogAppointmentAddSave;

    private Patient patient;

    private ViewModelAppointment viewModelAppointment;
    private ViewModelPatient viewModelPatient;
    private AdapterSpinnerPatients adapterPatient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModelPatient = ViewModelProviders.of(DialogAddAppointment.this).get(ViewModelPatient.class);
        viewModelAppointment = ViewModelProviders.of(DialogAddAppointment.this).get(ViewModelAppointment.class);

        final View view = layoutInflater.inflate(R.layout.dialog_add_appointment, null);

        edtDialogAppointmentAddOperation = view.findViewById(R.id.edtDialogAppointmentAddOperation);
        spnDialogAppointmentAddName = view.findViewById(R.id.spnDialogAppointmentAddName);
        dpDialogAppointmentAddDate = view.findViewById(R.id.dpDialogAppointmentAddDate);
        dpDialogAppointmentAddTime = view.findViewById(R.id.dpDialogAppointmentAddTime);
        btnDialogAppointmentAddSave = view.findViewById(R.id.btnDialogAppointmentAddSave);

        patients = new ArrayList<>();

        viewModelPatient.getAll().observe(getViewLifecycleOwner(), new Observer<List<Patient>>() {
            @Override
            public void onChanged(List<Patient> patientss) {
                patients = patientss;

                final AdapterSpinnerPatients adapterPatient = new AdapterSpinnerPatients(view.getContext(),
                        android.R.layout.simple_spinner_item, patients);
                adapterPatient.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnDialogAppointmentAddName.setAdapter(adapterPatient);
                spnDialogAppointmentAddName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        btnDialogAppointmentAddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String operation = edtDialogAppointmentAddOperation.getText().toString();

                int patientId = patient.getId();

                String name = patient.getName();
                      /*  dpDialogAppointmentAddTime.getHour()+(dpDialogAppointmentAddTime.getMinute() < 10 ? "0" + dpDialogAppointmentAddTime.getMinute() : dpDialogAppointmentAddTime.getMinute())
                         + dpDialogAppointmentAddDate.getDayOfMonth() + (dpDialogAppointmentAddDate.getMonth() + 1) +
                        dpDialogAppointmentAddDate.getYear();
                        "" + dpDialogAppointmentAddDate.getYear()
                                + (dpDialogAppointmentAddDate.getMonth() + 1)
                                + dpDialogAppointmentAddDate.getDayOfMonth()
                                + dpDialogAppointmentAddTime.getHour()
                                + (dpDialogAppointmentAddTime.getMinute() < 10 ? "0" + dpDialogAppointmentAddTime.getMinute() : dpDialogAppointmentAddTime.getMinute());*/

                      int month=(dpDialogAppointmentAddDate.getMonth() + 1);
                String date = "" + dpDialogAppointmentAddDate.getYear()
                        +(month  <10 ? "0" +month : month)
                        +(dpDialogAppointmentAddDate.getDayOfMonth()   <10 ?"0" + dpDialogAppointmentAddDate.getDayOfMonth() : dpDialogAppointmentAddDate.getDayOfMonth())
                        +(dpDialogAppointmentAddTime.getHour()         <10 ?"0" + dpDialogAppointmentAddTime.getHour()       : dpDialogAppointmentAddTime.getHour())
                        +(dpDialogAppointmentAddTime.getMinute()       <10 ?"0" + dpDialogAppointmentAddTime.getMinute()     : dpDialogAppointmentAddTime.getMinute());

                Appointment appointment = new Appointment(name, date, operation, patientId);

                viewModelAppointment.insert(appointment);
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
