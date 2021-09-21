package com.mbs.view.viewholder;

import android.view.View;
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

    public ViewHolderPatient(@NonNull View view)
    {
        super(view);
        this.view=view;
        imgItemPatientImage=view.findViewById(R.id.imgItemPatientImage);
        txtItemPatientName=view.findViewById(R.id.txtItemPatientName);
        txtItemPatientAge=view.findViewById(R.id.txtItemPatientAge);
        txtItemPatientPhone=view.findViewById(R.id.txtItemPatientPhone);
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
}
