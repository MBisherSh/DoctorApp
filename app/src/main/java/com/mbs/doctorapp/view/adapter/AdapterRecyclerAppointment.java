package com.mbs.doctorapp.view.adapter;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mbs.doctorapp.R;
import com.mbs.doctorapp.model.entity.Appointment;
import com.mbs.doctorapp.view.fragments.appointments.AppointmentsFragment;
import com.mbs.doctorapp.view.viewholder.ViewHolderAppointment;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterRecyclerAppointment extends RecyclerView.Adapter<ViewHolderAppointment> {
    private Fragment fragment;
    private List<Appointment> appointments;

    public AdapterRecyclerAppointment(Fragment fragment, List<Appointment> appointments) {
        this.fragment = fragment;
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public ViewHolderAppointment onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new ViewHolderAppointment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAppointment viewHolderAppointment, int position) {
        final Appointment appointment = appointments.get(position);
        viewHolderAppointment.getTxtItemAppointmentName().setText(appointment.getPatient());
        viewHolderAppointment.getTxtItemAppointmentOperation().setText(appointment.getOperation());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        try {

            date = sdf.parse(appointment.getDate());
            Log.d("shit3", date.toString()+" (date)");
        } catch (ParseException ignored) {
            ignored.printStackTrace();
        }
        SimpleDateFormat f = new SimpleDateFormat("hh:mm a dd/MM/yyyy");
        Log.d("shit1", appointment.getDate()+" (appoint date)");
        Log.d("shit2", f.format(date)+" (format date)");
        viewHolderAppointment.getTxtItemAppointmentDate().setText(f.format(date));


        viewHolderAppointment.getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(fragment.getContext())
                        .setTitle("Delete Appointment")
                        .setMessage("Do you really want to Delete this Appointment?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                ((AppointmentsFragment) fragment).deleteAppointment(appointment);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public void setData(List<Appointment> appointments) {
        this.appointments = appointments;
        notifyDataSetChanged();
    }
}
