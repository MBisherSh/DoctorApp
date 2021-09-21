package com.mbs.doctorapp.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.mbs.doctorapp.R;
import com.mbs.doctorapp.model.entity.Patient;
import com.mbs.doctorapp.model.entity.Payment;
import com.mbs.doctorapp.viewmodel.ViewModelPatient;

import io.paperdb.Paper;


public class DialogAddAmount extends DialogFragment
{
    public DialogAddAmount(Patient patient)
    {
        super();
        this.patient = patient;
    }

    Context context = getContext();

    private TextInputEditText edtDialogAddAmount;

    private Button btnDialogAddAmountSave;

    private Patient patient;

    private ViewModelPatient viewModelPatient;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModelPatient = ViewModelProviders.of(DialogAddAmount.this).get(ViewModelPatient.class);

        final View view = layoutInflater.inflate(R.layout.dialog_add_amount, null);

        edtDialogAddAmount = view.findViewById(R.id.edtDialogAddAmount);
        btnDialogAddAmountSave = view.findViewById(R.id.btnDialogAddAmountSave);

        btnDialogAddAmountSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(edtDialogAddAmount.getText().toString().equals(""))
                    Toast.makeText(context, "Please Enter Money Amount !", Toast.LENGTH_SHORT).show();
                else {
                    patient.setPayments(patient.getPayments()+Integer.parseInt(edtDialogAddAmount.getText().toString()));
                    viewModelPatient.update(patient);
                    Paper.init(getContext());
                    Paper.book().write("OnePatient", patient);
                    Intent intent = getActivity().getIntent();
                    getActivity().finish();
                    startActivity(intent);
                    dismiss();

                }
            }
        });
        return view;
    }



}