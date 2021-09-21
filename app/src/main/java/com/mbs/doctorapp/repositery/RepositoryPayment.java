package com.mbs.doctorapp.repositery;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mbs.doctorapp.model.dao.PaymentDao;
import com.mbs.doctorapp.model.database.RoomDatabaseDoctorApp;
import com.mbs.doctorapp.model.entity.Payment;

import java.util.List;

public class RepositoryPayment
{
    private PaymentDao paymentDao;
    private LiveData<List<Payment>> payments;

    public RepositoryPayment(Application application)
    {
        RoomDatabaseDoctorApp dp = RoomDatabaseDoctorApp.getDatabase(application);
        paymentDao=dp.getPaymentDao();
    }

    public LiveData<List<Payment>> getAll()
    {
        payments=paymentDao.getAll();
        return payments;
    }
    public LiveData<List<Payment>> getAllByPatient(int pID)
    {
        payments=paymentDao.getAllByPatient(pID);
        return payments;
    }

    public void insert(Payment... payments)
    {
        new TaskInsert(paymentDao).execute(payments);
    }
    public void delete(Payment... payments)
    {
        new TaskDelete(paymentDao).execute(payments);
    }
    public void update(Payment... payments)
    {
        new TaskUpdate(paymentDao).execute(payments);
    }

    private static class TaskInsert extends AsyncTask<Payment, Void, Void>
    {
    private PaymentDao paymentDao;

    public TaskInsert(PaymentDao paymentDao)
    {
        this.paymentDao = paymentDao;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Payment... payments)
    {
        for(Payment payment: payments)
        {
            paymentDao.insert(payment);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
    }
}

    private static class TaskDelete extends AsyncTask<Payment, Void, Void>
    {
        private PaymentDao paymentDao;

        public TaskDelete(PaymentDao paymentDao)
        {
            this.paymentDao = paymentDao;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Payment... payments)
        {
            for(Payment payment: payments)
            {
                paymentDao.delete(payment);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
        }
    }

    private static class TaskUpdate extends AsyncTask<Payment, Void, Void>
    {
        private PaymentDao paymentDao;

        public TaskUpdate(PaymentDao paymentDao)
        {
            this.paymentDao = paymentDao;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Payment... payments)
        {
            for(Payment payment: payments)
            {
                paymentDao.update(payment);
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

