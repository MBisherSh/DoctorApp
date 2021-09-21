package com.mbs.doctorapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mbs.doctorapp.model.entity.Payment;
import com.mbs.doctorapp.repositery.RepositoryPayment;

import java.util.List;

public class ViewModelPayment extends AndroidViewModel
{
    private RepositoryPayment repositoryPayment;
    private LiveData<List<Payment>> payments;

    public ViewModelPayment(@NonNull Application application)
    {
        super(application);
        repositoryPayment=new RepositoryPayment(application);
    }

    public void insert(Payment... payments){
        repositoryPayment.insert(payments);
    }
    public void delete(Payment... payments){
        repositoryPayment.delete(payments);
    }
    public void update(Payment... payments){
        repositoryPayment.update(payments);
    }
    public LiveData<List<Payment>> getAll(){
       payments= repositoryPayment.getAll();
       return payments;
    }
    public LiveData<List<Payment>> getAllByID(int pID){
        payments= repositoryPayment.getAllByPatient(pID);
        return payments;
    }
}
