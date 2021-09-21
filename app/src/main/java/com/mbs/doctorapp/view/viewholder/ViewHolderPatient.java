package com.mbs.doctorapp.view.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mbs.doctorapp.R;

public class ViewHolderPatient extends RecyclerView.ViewHolder
{
    private View view;

    private ImageView imgItemPatientImage;
    private TextView txtItemPatientName;
    private TextView txtItemPatientAge;
    private TextView txtItemPatientPhone;
    private ImageButton btnItemPatientCall;

    public ViewHolderPatient(@NonNull View view)
    {
        super(view);
        this.view=view;
        imgItemPatientImage=view.findViewById(R.id.imgItemPatientImage);
        txtItemPatientName=view.findViewById(R.id.txtItemPatientName);
        txtItemPatientAge=view.findViewById(R.id.txtItemPatientAge);
        txtItemPatientPhone=view.findViewById(R.id.txtItemPatientPhone);
        btnItemPatientCall=view.findViewById(R.id.btnItemPatientCall);
    }

    public View getView() {
        return view;
    }

    public ImageView getImgItemPatientImage() {
        return imgItemPatientImage;
    }

    public TextView getTxtItemPatientName() {
        return txtItemPatientName;
    }

    public TextView getTxtItemPatientAge() {
        return txtItemPatientAge;
    }

    public TextView getTxtItemPatientPhone() {
        return txtItemPatientPhone;
    }

    public ImageButton getBtnItemPatientCall()
    {
        return btnItemPatientCall;
    }
}
