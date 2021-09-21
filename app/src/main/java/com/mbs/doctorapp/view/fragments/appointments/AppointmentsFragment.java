package com.mbs.doctorapp.view.fragments.appointments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mbs.doctorapp.R;
import com.mbs.doctorapp.model.entity.Appointment;
import com.mbs.doctorapp.view.adapter.AdapterRecyclerAppointment;
import com.mbs.doctorapp.view.fragments.DialogAddAppointment;
import com.mbs.doctorapp.view.fragments.DialogAddPatient;
import com.mbs.doctorapp.viewmodel.ViewModelAppointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsFragment extends Fragment
{

    private AppointmentsViewModel appointmentsViewModel;

    private ViewModelAppointment viewModelAppointment;

    private RecyclerView recyclerAppointmentsAll;
    private RecyclerView.LayoutManager layoutManager;
    private List<Appointment> appointments;
    private AdapterRecyclerAppointment adapterRecyclerAppointment;

    private FloatingActionButton fabAddAppointment;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        appointmentsViewModel =
                ViewModelProviders.of(this).get(AppointmentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_appointments, container, false);

        viewModelAppointment= ViewModelProviders.of(this).get(ViewModelAppointment.class);

        recyclerAppointmentsAll= (RecyclerView) root.findViewById(R.id.recyclerAppointmentsAll);
        recyclerAppointmentsAll.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerAppointmentsAll.setLayoutManager(layoutManager);
        appointments=new ArrayList<>();
        adapterRecyclerAppointment=new AdapterRecyclerAppointment(this,appointments);
        recyclerAppointmentsAll.setAdapter(adapterRecyclerAppointment);
        viewModelAppointment.getAll().observe(getViewLifecycleOwner(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments)
            {
                AppointmentsFragment.this.appointments=appointments;
                adapterRecyclerAppointment.setData(appointments);
            }
        });

        fabAddAppointment=root.findViewById(R.id.fabAddAppointment);
        fabAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddAppointment dialogAddAppointment=new DialogAddAppointment();
                dialogAddAppointment.show(getActivity().getSupportFragmentManager(), "DialogAddAppointment");
            }
        });

        return root;


    }
    public void deleteAppointment(Appointment appointment){
        viewModelAppointment.delete(appointment);
    }
}