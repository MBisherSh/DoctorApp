package com.mbs.doctorapp.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "Patient")
public class Patient
{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;
    @ColumnInfo(name = "Name")
    private String name;
    @ColumnInfo(name = "Sex")
    private String sex;
    @ColumnInfo(name = "Address")
    private String address;
    @ColumnInfo(name = "Phone")
    private String phone;
    @ColumnInfo(name = "Payments")
    private int payments;
    @ColumnInfo(name = "Illnesses")
    private String illnesses;
    @ColumnInfo(name = "Drugs")
    private String drugs;
    @ColumnInfo(name = "Review")
    private String review;
    @ColumnInfo(name = "Age")
    private int age;
    @ColumnInfo(name = "Image" ,typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public Patient( String name, String sex, String address, String phone, String illnesses, String drugs, String review, int age, byte[] image, int payments){
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
        this.illnesses = illnesses;
        this.drugs = drugs;
        this.review = review;
        this.age = age;
        this.payments=payments;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getIllnesses() {
        return illnesses;
    }

    public String getDrugs() {
        return drugs;
    }

    public String getReview() {
        return review;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIllnesses(String illnesses) {
        this.illnesses = illnesses;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPayments() {
        return payments;
   }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", illnesses='" + illnesses + '\'' +
                ", drugs='" + drugs + '\'' +
                ", review='" + review + '\'' +
                ", age=" + age +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}

