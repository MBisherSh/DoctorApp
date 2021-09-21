package com.mbs.doctorapp.view.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mbs.doctorapp.R;
import com.mbs.doctorapp.model.entity.Appointment;
import com.mbs.doctorapp.view.activity.OnePatientActivity;
import com.mbs.doctorapp.view.fragments.appointments.AppointmentsFragment;
import com.mbs.doctorapp.view.viewholder.ViewHolderAppointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterRecyclerOnePatientAppointments extends RecyclerView.Adapter<ViewHolderAppointment> {
    private Context context;
    private List<Appointment> appointments;

    public AdapterRecyclerOnePatientAppointments(Context context, List<Appointment> appointments) {
        this.context = context;
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public ViewHolderAppointment onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appointment, parent, false);
        return new ViewHolderAppointment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAppointment viewHolderAppointment, int position) {
        final Appointment appointment = appointments.get(position);

        viewHolderAppointment.getTxtItemAppointmentOperation().setText(appointment.getOperation());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        try {

            date = sdf.parse(appointment.getDate());
        } catch (ParseException ignored) {
            ignored.printStackTrace();
        }
        SimpleDateFormat f = new SimpleDateFormat("hh:mm a dd/MM/yyyy");

        viewHolderAppointment.getTxtItemAppointmentDate().setText(f.format(date));


        viewHolderAppointment.getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Appointment")
                        .setMessage("Do you really want to Delete this Appointment?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                ((OnePatientActivity) context).deleteAppointment(appointment);
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
