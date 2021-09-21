package com.mbs.doctorapp.view.fragments.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mbs.doctorapp.R;
import com.mbs.doctorapp.model.entity.Appointment;
import com.mbs.doctorapp.model.entity.Patient;
import com.mbs.doctorapp.model.entity.Payment;
import com.mbs.doctorapp.view.fragments.appointments.AppointmentsFragment;
import com.mbs.doctorapp.view.fragments.patients.PatientsFragment;
import com.mbs.doctorapp.viewmodel.ViewModelAppointment;
import com.mbs.doctorapp.viewmodel.ViewModelPatient;
import com.mbs.doctorapp.viewmodel.ViewModelPayment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView txtPatientsNumber;
    private TextView txtAppointmentsNumber;
    private TextView txtTotal;
    private int totalMonth;
    private Date date=new Date() ;
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
    private ViewModelPatient viewModelPatient;
    private ViewModelAppointment viewModelAppointment;
    private ViewModelPayment viewModelPayment;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        txtPatientsNumber=root.findViewById(R.id.txtPatientsNumber);
        txtAppointmentsNumber=root.findViewById(R.id.txtAppointmentsNumber);
        txtTotal=root.findViewById(R.id.txtTotal);
        viewModelPatient=ViewModelProviders.of(this).get(ViewModelPatient.class);
        viewModelAppointment=ViewModelProviders.of(this).get(ViewModelAppointment.class);
        viewModelPayment=ViewModelProviders.of(this).get(ViewModelPayment.class);

        viewModelPatient.getAll().observe(getViewLifecycleOwner(), new Observer<List<Patient>>() {
            @Override
            public void onChanged(List<Patient> patients)
            {
                txtPatientsNumber.setText(patients.size()+"");
            }
        });
        viewModelAppointment.getAll().observe(getViewLifecycleOwner(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments)
            {
                int apps=0;
                for (Appointment p:appointments) {

                    try {
                        date = sdf.parse(p.getDate());
                    } catch (ParseException ignored) {
                        ignored.printStackTrace();
                    }
                    Date currentTime = Calendar.getInstance().getTime();

                    if (date.getDay() == currentTime.getDay() && date.getMonth()==currentTime.getMonth() && date.getYear()==currentTime.getYear()) {
                        apps++;
                    }
                }

                txtAppointmentsNumber.setText(apps+"");
            }
        });
        viewModelPayment.getAll().observe(getViewLifecycleOwner(), new Observer<List<Payment>>() {
            @Override
            public void onChanged(List<Payment> payments)
            {
                if(payments.size()==0)
                totalMonth=0;
                else {
                    totalMonth=0;

                for (Payment p:payments)
                {
                    try {
                        date = sdf.parse(p.getDate());
                    } catch (ParseException ignored) {
                        ignored.printStackTrace();
                    }
                    Date currentTime = Calendar.getInstance().getTime();
                    Log.d("shit",date.getMonth()+"" );
                    Log.d("shit",currentTime.getMonth()+"" );
                    if(date.getMonth()==currentTime.getMonth()){
                        totalMonth+=p.getValue();
                    }

                }
                }
                txtTotal.setText(totalMonth+"");
            }
        });
        return root;
    }
}