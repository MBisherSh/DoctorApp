package com.mbs.doctorapp.view.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mbs.doctorapp.R;

public class ViewHolderPayment extends RecyclerView.ViewHolder
{
    private View view;

    private TextView txtItemPaymentName;
    private TextView txtItemPaymentOperation;
    private TextView txtItemPaymentDate;
    private TextView txtItemPaymentValue;

    public ViewHolderPayment(@NonNull View view)
    {
        super(view);
        this.view=view;
        txtItemPaymentName=view.findViewById(R.id.txtItemPaymentName);
        txtItemPaymentOperation=view.findViewById(R.id.txtItemPaymentOperation);
        txtItemPaymentValue=view.findViewById(R.id.txtItemPaymentValue);
        txtItemPaymentDate=view.findViewById(R.id.txtItemPaymentDate);
    }

    public View getView() {
        return view;
    }


    public TextView getTxtItemPaymentName() {
        return txtItemPaymentName;
    }

    public TextView getTxtItemPaymentOperation() {
        return txtItemPaymentOperation;
    }

    public TextView getTxtItemPaymentDate() {
        return txtItemPaymentDate;
    }

    public TextView getTxtItemPaymentValue() {
        return txtItemPaymentValue;
    }
}
