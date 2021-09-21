package com.mbs.doctorapp.repositery;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.mbs.doctorapp.model.dao.AppointmentDao;
import com.mbs.doctorapp.model.database.RoomDatabaseDoctorApp;
import com.mbs.doctorapp.model.entity.Appointment;

import java.util.List;

public class RepositoryAppointment {
    private AppointmentDao appointmentDao;
    private LiveData<List<Appointment>> appointments;

    public RepositoryAppointment(Application application) {
        RoomDatabaseDoctorApp dp = RoomDatabaseDoctorApp.getDatabase(application);
        appointmentDao = dp.getAppointmentDao();
    }

    public LiveData<List<Appointment>> getAll() {
        appointments = appointmentDao.getAll();
        return appointments;
    }
    public LiveData<List<Appointment>> getAllByPatient(int pID) {
        appointments = appointmentDao.getAllByPatient(pID);
        return appointments;
    }
    public void insert(Appointment... appointments) {
        new TaskInsert(appointmentDao).execute(appointments);
    }

    public void delete(Appointment... appointments) {
        new TaskDelete(appointmentDao).execute(appointments);
    }

    public void update(Appointment... appointments) {
        new TaskUpdate(appointmentDao).execute(appointments);
    }

    private static class TaskInsert extends AsyncTask<Appointment, Void, Void> {
        private AppointmentDao appointmentDao;

        public TaskInsert(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Appointment... appointments) {
            for (Appointment appointment : appointments) {
                appointmentDao.insert(appointment);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class TaskDelete extends AsyncTask<Appointment, Void, Void> {
        private AppointmentDao appointmentDao;

        public TaskDelete(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Appointment... appointments) {
            for (Appointment appointment : appointments) {
                appointmentDao.delete(appointment);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class TaskUpdate extends AsyncTask<Appointment, Void, Void> {
        private AppointmentDao appointmentDao;

        public TaskUpdate(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Appointment... appointments) {
            for (Appointment appointment : appointments) {
                appointmentDao.update(appointment);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}

