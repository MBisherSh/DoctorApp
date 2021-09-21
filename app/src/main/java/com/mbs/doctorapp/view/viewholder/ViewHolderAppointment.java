package com.mbs.doctorapp.view.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mbs.doctorapp.R;

public class ViewHolderAppointment extends RecyclerView.ViewHolder
{
    private View view;

    private TextView txtItemAppointmentName;
    private TextView txtItemAppointmentOperation;
    private TextView txtItemAppointmentDate;

    public ViewHolderAppointment(@NonNull View view)
    {
        super(view);
        this.view=view;
        txtItemAppointmentName=view.findViewById(R.id.txtItemAppointmentName);
        txtItemAppointmentOperation=view.findViewById(R.id.txtItemAppointmentOperation);
        txtItemAppointmentDate=view.findViewById(R.id.txtItemAppointmentDate);
    }

    public View getView() {
        return view;
    }


    public TextView getTxtItemAppointmentName() {
        return txtItemAppointmentName;
    }

    public TextView getTxtItemAppointmentOperation() {
        return txtItemAppointmentOperation;
    }

    public TextView getTxtItemAppointmentDate() {
        return txtItemAppointmentDate;
    }
}
