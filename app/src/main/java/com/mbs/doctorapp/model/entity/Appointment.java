package com.mbs.doctorapp.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Appointment" , foreignKeys = @ForeignKey(entity = Patient.class,
                                                                                 parentColumns = "ID",
                                                                                 childColumns = "PatientID",
                                                                                 onDelete = ForeignKey.CASCADE))
public class Appointment {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;
    @ColumnInfo(name = "Patient")
    private String patient;
    @ColumnInfo(name = "Date")
    private String date;
    @ColumnInfo(name = "Operation")
    private String operation;
    @ColumnInfo(name = "PatientID")
    private int patientID;

    public Appointment( String patient, String date, String operation, int patientID) {
        this.patient = patient;
        this.date = date;
        this.operation = operation;
        this.patientID = patientID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

}