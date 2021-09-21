package com.mbs.doctorapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mbs.doctorapp.model.entity.Appointment;
import com.mbs.doctorapp.repositery.RepositoryAppointment;


import java.util.List;

public class ViewModelAppointment extends AndroidViewModel
{
    private RepositoryAppointment repositoryAppointment;
    private LiveData<List<Appointment>> appointments;

    public ViewModelAppointment(@NonNull Application application)
    {
        super(application);
        repositoryAppointment=new RepositoryAppointment(application);
    }

    public void insert(Appointment... appointments){
        repositoryAppointment.insert(appointments);
    }
    public void delete(Appointment... appointments){
        repositoryAppointment.delete(appointments);
    }
    public void update(Appointment... appointments){
        repositoryAppointment.update(appointments);
    }
    public LiveData<List<Appointment>> getAll(){
       appointments= repositoryAppointment.getAll();
       return appointments;
    }
    public LiveData<List<Appointment>> getAllByPatient(int pID){
        appointments= repositoryAppointment.getAllByPatient(pID);
        return appointments;
    }
}
