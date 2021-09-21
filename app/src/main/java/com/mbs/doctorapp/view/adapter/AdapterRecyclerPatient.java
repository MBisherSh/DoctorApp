package com.mbs.doctorapp.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mbs.doctorapp.R;
import com.mbs.doctorapp.model.entity.Patient;
import com.mbs.doctorapp.view.activity.MainActivity;
import com.mbs.doctorapp.view.activity.OnePatientActivity;
import com.mbs.doctorapp.view.fragments.appointments.AppointmentsFragment;
import com.mbs.doctorapp.view.fragments.patients.PatientsFragment;
import com.mbs.doctorapp.view.viewholder.ViewHolderPatient;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class AdapterRecyclerPatient extends RecyclerView.Adapter<ViewHolderPatient> implements Filterable {

    private Fragment fragment;
    private List<Patient> patients;
    private List<Patient> patientsF;

    public AdapterRecyclerPatient(Fragment fragment, List<Patient> patients) {
        this.fragment = fragment;
        this.patients = patients;
        this.patientsF = patients;
    }

    @NonNull
    @Override
    public ViewHolderPatient onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.item_patient, parent, false);
        return new ViewHolderPatient(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPatient viewHolderPatient, int position) {
        final Patient patient = patients.get(position);
        viewHolderPatient.getTxtItemPatientName().setText(patient.getName());
        viewHolderPatient.getTxtItemPatientAge().setText(patient.getAge() + "");
        viewHolderPatient.getTxtItemPatientPhone().setText(patient.getPhone());
if (patient.getImage()==null)
    Glide.with(fragment.getContext()).load(R.drawable.ic_patient).into(viewHolderPatient.getImgItemPatientImage());
   else
       Glide.with(fragment.getContext()).load(patient.getImage()).into(viewHolderPatient.getImgItemPatientImage());


        viewHolderPatient.getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder(fragment.getContext())
                        .setTitle("Delete Patient")
                        .setMessage("Do you really want to Delete this Patient?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                ((PatientsFragment) fragment).deletePatient(patient);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

                return false;
            }
        });

        viewHolderPatient.getTxtItemPatientPhone().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewHolderPatient.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.init(fragment.getContext());
                Paper.book().write("OnePatient", patient);

                Intent intent = new Intent(fragment.getContext(), OnePatientActivity.class);
                fragment.getContext().startActivity(intent);

            }
        });

        viewHolderPatient.getBtnItemPatientCall().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + patient.getPhone()));
                fragment.getContext().startActivity(callIntent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return patients.size();
    }

    public void setData(List<Patient> patients) {
        this.patients = patients;
        this.patientsF = patients;
        notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Patient> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(patientsF);
            } else {
                String filterPattern = constraint.toString().toLowerCase();

                for (Patient item : patientsF) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.count = filteredList.size();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            patients = (List<Patient>) results.values;
            notifyDataSetChanged();
        }
    };

}
