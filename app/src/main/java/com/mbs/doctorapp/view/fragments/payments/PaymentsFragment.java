package com.mbs.doctorapp.view.fragments.payments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mbs.doctorapp.R;
import com.mbs.doctorapp.model.entity.Payment;

import com.mbs.doctorapp.view.adapter.AdapterRecyclerPayment;

import com.mbs.doctorapp.view.fragments.DialogAddPayment;
import com.mbs.doctorapp.viewmodel.ViewModelPayment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PaymentsFragment extends Fragment {

    private PaymentsViewModel paymentsViewModel;
    private ViewModelPayment viewModelPayment;
    private RecyclerView recyclerPaymentsAll;
    private RecyclerView.LayoutManager layoutManager;
    private List<Payment> payments;
    private AdapterRecyclerPayment adapterRecyclerPayment;
    private TextView txtPaymentsTotal;
    private TextView txtPaymentsTotalMonth;
    private int total;
    private int totalMonth;
    private Date date=new Date() ;
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
    private FloatingActionButton fabAddPayment;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        paymentsViewModel =
                ViewModelProviders.of(this).get(PaymentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_payments, container, false);
        viewModelPayment= ViewModelProviders.of(this).get(ViewModelPayment.class);

        txtPaymentsTotal=root.findViewById(R.id.txtPaymentsTotal);
        txtPaymentsTotalMonth=root.findViewById(R.id.txtPaymentsTotalMonth);
        recyclerPaymentsAll= (RecyclerView) root.findViewById(R.id.recyclerPaymentsAll);
        recyclerPaymentsAll.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerPaymentsAll.setLayoutManager(layoutManager);
        payments=new ArrayList<>();
        adapterRecyclerPayment=new AdapterRecyclerPayment(this,payments);
        recyclerPaymentsAll.setAdapter(adapterRecyclerPayment);






        viewModelPayment.getAll().observe(getViewLifecycleOwner(), new Observer<List<Payment>>() {
            @Override
            public void onChanged(List<Payment> payments)
            {

                PaymentsFragment.this.payments=payments;
                adapterRecyclerPayment.setData(payments);
                if(payments.isEmpty()){
                total=0;
                totalMonth=0;}
                else {total=0;
                    totalMonth=0;
                for (Payment p:payments) {
                    total += p.getValue();
                    try {
                        date = sdf.parse(p.getDate());
                    } catch (ParseException ignored) {
                        ignored.printStackTrace();
                    }
                    Date currentTime = Calendar.getInstance().getTime();
                    Log.d("shit",date.getMonth()+"" );
                    Log.d("shit",currentTime.getMonth()+"" );
                    if (date.getMonth() == currentTime.getMonth() && date.getYear()==currentTime.getYear()) {
                        totalMonth += p.getValue();
                    }
                }
                }
                txtPaymentsTotal.setText(total+"");
                txtPaymentsTotalMonth.setText(totalMonth+"");
            }
        });

        fabAddPayment=root.findViewById(R.id.fabAddPayment);
        fabAddPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DialogAddPayment dialogAddPayment=new DialogAddPayment();
               dialogAddPayment.show(getActivity().getSupportFragmentManager(), "DialogAddPayment");
            }
        });


        return root;
    }
    public void deletePayment(Payment payment){
        viewModelPayment.delete(payment);
    }
}