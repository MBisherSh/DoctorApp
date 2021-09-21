package com.mbs.doctorapp.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "Payment" , foreignKeys = @ForeignKey(entity = Patient.class,
                                                                                     parentColumns = "ID",
                                                                                     childColumns = "PatientID",
                                                                                     onDelete = ForeignKey.CASCADE))
public class Payment {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;
    @ColumnInfo(name = "Patient")
    private String patient;
    @ColumnInfo(name = "Operation")
    private String operation;
    @ColumnInfo(name = "Date")
    private String date;
    @ColumnInfo(name = "Value")
    private int value;
    @ColumnInfo(name = "PatientID")
    private int patientID;

    public Payment(String patient, String operation, String date, int value, int patientID) {
        this.patient = patient;
        this.operation = operation;
        this.date = date;
        this.value = value;
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
}
