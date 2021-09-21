package com.mbs.doctorapp.model.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.mbs.doctorapp.model.entity.Payment;

import java.util.List;

@Dao
public interface PaymentDao
{
    @Insert
    void insert(Payment payment);

    @Delete
    void delete(Payment payment);

    @Update
    void update(Payment payment);

    @Query("SELECT * from Payment order by date desc")
    LiveData<List<Payment>> getAll();
    @Query("SELECT * from Payment WHERE PatientID IN (:pID) order by date desc")
    LiveData<List<Payment>> getAllByPatient(int pID);
}
