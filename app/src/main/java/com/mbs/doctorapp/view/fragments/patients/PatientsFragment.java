package com.mbs.doctorapp.view.fragments.patients;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
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
import com.mbs.doctorapp.model.entity.Patient;
import com.mbs.doctorapp.view.adapter.AdapterRecyclerPatient;
import com.mbs.doctorapp.view.fragments.DialogAddPatient;
import com.mbs.doctorapp.viewmodel.ViewModelPatient;

import java.util.ArrayList;
import java.util.List;

public class PatientsFragment extends Fragment {

    private PatientsViewModel patientsViewModel;

    private ViewModelPatient viewModelPatient;

    private SearchView searchPatients;

    private RecyclerView recyclerPatientsAll;
    private RecyclerView.LayoutManager layoutManager;
    private List<Patient> patients;
    private AdapterRecyclerPatient adapterRecyclerPatient;
    private FloatingActionButton fabAddPatient;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        Context context=getContext();
        patientsViewModel =
                ViewModelProviders.of(this).get(PatientsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_patients, container, false);


        searchPatients=root.findViewById(R.id.searchPatients);

        searchPatients.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterRecyclerPatient.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                adapterRecyclerPatient.getFilter().filter(newText);
                return true;
            }

        });


        viewModelPatient= ViewModelProviders.of(this).get(ViewModelPatient.class);

        recyclerPatientsAll=  root.findViewById(R.id.recyclerPatientsAll);
        recyclerPatientsAll.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this.getContext());
        recyclerPatientsAll.setLayoutManager(layoutManager);
        patients=new ArrayList<>();
        adapterRecyclerPatient=new AdapterRecyclerPatient(this,patients);
        recyclerPatientsAll.setAdapter(adapterRecyclerPatient);
        viewModelPatient.getAll().observe(getViewLifecycleOwner(), new Observer<List<Patient>>() {
            @Override
            public void onChanged(List<Patient> patients)
            {
                PatientsFragment.this.patients=patients;
                adapterRecyclerPatient.setData(patients);
            }
        });
        fabAddPatient=root.findViewById(R.id.fabAddPatient);
        fabAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                DialogAddPatient dialog = new DialogAddPatient();
                dialog.show(getActivity().getSupportFragmentManager(), "DialogAddPatient");

            }
        });






        return root;
    }
    public void deletePatient(Patient patient){
        viewModelPatient.delete(patient);
    }
}