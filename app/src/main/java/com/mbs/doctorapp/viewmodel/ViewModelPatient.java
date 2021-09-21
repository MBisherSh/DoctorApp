package com.mbs.doctorapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mbs.doctorapp.model.entity.Patient;
import com.mbs.doctorapp.repositery.RepositoryPatient;
import java.util.List;

public class ViewModelPatient extends AndroidViewModel
{
    private RepositoryPatient repositoryPatient;
    private LiveData<List<Patient>> patients;

    public ViewModelPatient(@NonNull Application application)
    {
        super(application);
        repositoryPatient=new RepositoryPatient(application);
    }

    public void insert(Patient... patients){
        repositoryPatient.insert(patients);
    }
    public void delete(Patient... patients){
        repositoryPatient.delete(patients);
    }
    public void update(Patient... patients){
        repositoryPatient.update(patients);
    }
    public LiveData<List<Patient>> getAll(){
       patients= repositoryPatient.getAll();
       return patients;
    }
}
