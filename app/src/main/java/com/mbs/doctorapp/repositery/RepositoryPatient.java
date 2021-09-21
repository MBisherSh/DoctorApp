package com.mbs.doctorapp.repositery;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mbs.doctorapp.model.dao.PatientDao;
import com.mbs.doctorapp.model.database.RoomDatabaseDoctorApp;
import com.mbs.doctorapp.model.entity.Patient;

import java.util.List;

public class RepositoryPatient
{
    private PatientDao patientDao;
    private LiveData<List<Patient>> patients;

    public RepositoryPatient(Application application)
    {
        RoomDatabaseDoctorApp dp = RoomDatabaseDoctorApp.getDatabase(application);
        patientDao=dp.getPatientDao();
    }

    public LiveData<List<Patient>> getAll()
    {
        patients=patientDao.getAll();
        return patients;
    }

    public void insert(Patient... patients)
    {
        new TaskInsert(patientDao).execute(patients);
    }
    public void delete(Patient... patients)
    {
        new TaskDelete(patientDao).execute(patients);
    }
    public void update(Patient... patients)
    {
        new TaskUpdate(patientDao).execute(patients);
    }

    private static class TaskInsert extends AsyncTask<Patient, Void, Void>
    {
    private PatientDao patientDao;

    public TaskInsert(PatientDao patientDao)
    {
        this.patientDao = patientDao;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Patient... patients)
    {
        for(Patient patient: patients)
        {
            patientDao.insert(patient);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
    }
}

    private static class TaskDelete extends AsyncTask<Patient, Void, Void>
    {
        private PatientDao patientDao;

        public TaskDelete(PatientDao patientDao)
        {
            this.patientDao = patientDao;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Patient... patients)
        {
            for(Patient patient: patients)
            {
                patientDao.delete(patient);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
        }
    }

    private static class TaskUpdate extends AsyncTask<Patient, Void, Void>
    {
        private PatientDao patientDao;

        public TaskUpdate(PatientDao patientDao)
        {
            this.patientDao = patientDao;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Patient... patients)
        {
            for(Patient patient: patients)
            {
                patientDao.update(patient);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
        }
    }
}

