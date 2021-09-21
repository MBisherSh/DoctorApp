package com.mbs.doctorapp.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mbs.doctorapp.model.dao.AppointmentDao;
import com.mbs.doctorapp.model.dao.PatientDao;
import com.mbs.doctorapp.model.dao.PaymentDao;
import com.mbs.doctorapp.model.entity.Appointment;
import com.mbs.doctorapp.model.entity.Patient;
import com.mbs.doctorapp.model.entity.Payment;


@Database(entities = {Patient.class , Appointment.class, Payment.class}, version = 1)
public abstract class RoomDatabaseDoctorApp extends RoomDatabase
{

    public abstract PatientDao getPatientDao();
    public abstract PaymentDao getPaymentDao();
    public abstract AppointmentDao getAppointmentDao();

    private static volatile RoomDatabaseDoctorApp INSTANCE;

    public static RoomDatabaseDoctorApp getDatabase(final Context context) {
        if (INSTANCE == null)
        {
            synchronized (RoomDatabaseDoctorApp.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabaseDoctorApp.class, "DoctorAppDatabase")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

