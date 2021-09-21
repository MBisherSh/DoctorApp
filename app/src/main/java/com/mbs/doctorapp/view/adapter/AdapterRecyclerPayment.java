package com.mbs.doctorapp.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mbs.doctorapp.R;
import com.mbs.doctorapp.model.entity.Payment;
import com.mbs.doctorapp.view.fragments.patients.PatientsFragment;
import com.mbs.doctorapp.view.fragments.payments.PaymentsFragment;
import com.mbs.doctorapp.view.viewholder.ViewHolderPayment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterRecyclerPayment extends RecyclerView.Adapter<ViewHolderPayment> {
    private Fragment fragment;
    private List<Payment> payments;

    public AdapterRecyclerPayment(Fragment fragment, List<Payment> payments) {
        this.fragment = fragment;
        this.payments = payments;
    }

    @NonNull
    @Override
    public ViewHolderPayment onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.item_payment, parent, false);
        return new ViewHolderPayment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPayment viewHolderPayment, int position) {
        final Payment payment = payments.get(position);
        viewHolderPayment.getTxtItemPaymentName().setText(payment.getPatient());
        viewHolderPayment.getTxtItemPaymentOperation().setText(payment.getOperation());
        viewHolderPayment.getTxtItemPaymentValue().setText(payment.getValue()+"");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        try {
            Log.d("shit",payment.getDate() );
            date = sdf.parse(payment.getDate());
        } catch (ParseException ignored) {
            ignored.printStackTrace();
        }
        SimpleDateFormat f = new SimpleDateFormat("hh:mm a dd/MM/yyyy");

        viewHolderPayment.getTxtItemPaymentDate().setText(f.format(date));


        viewHolderPayment.getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(fragment.getContext())
                        .setTitle("Delete Payment")
                        .setMessage("Do you really want to Delete this Payment?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                ((PaymentsFragment)  fragment).deletePayment(payment);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public void setData(List<Payment> payments) {
        this.payments = payments;
        notifyDataSetChanged();
    }
}
