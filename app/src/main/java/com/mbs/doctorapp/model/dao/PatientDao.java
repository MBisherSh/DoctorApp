package com.mbs.doctorapp.model.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.mbs.doctorapp.model.entity.Patient;

import java.util.List;

@Dao
public interface PatientDao
{
    @Insert
    void insert(Patient patient);

    @Delete
    void delete(Patient patient);

    @Update
    void update(Patient patient);

    @Query("SELECT * from Patient ORDER BY Name")
    LiveData<List<Patient>> getAll();
}
