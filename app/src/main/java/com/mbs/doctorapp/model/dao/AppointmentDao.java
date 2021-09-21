package com.mbs.doctorapp.model.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mbs.doctorapp.model.entity.Appointment;
import com.mbs.doctorapp.model.entity.Payment;


import java.util.List;

@Dao
public interface AppointmentDao
{
    @Insert
    void insert(Appointment appointment);

    @Delete
    void delete(Appointment appointment);

    @Update
    void update(Appointment appointment);

    @Query("SELECT * from Appointment order by date asc")
    LiveData<List<Appointment>> getAll();

    @Query("SELECT * from Appointment WHERE PatientID IN (:pID) order by date asc")
    LiveData<List<Appointment>> getAllByPatient(int pID);
}
