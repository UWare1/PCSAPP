package com.example.pcs;

public class DoctorHelperClass {

    String DoctorID, Pincode, Email, NationalIDCard,
            Fullname, Address, RegularHospital, University, Gender, Date, _phoneNo,
            ProfileID, uid;
    public DoctorHelperClass(){}

    public DoctorHelperClass(String doctorID, String pincode, String email, String nationalIDCard, String fullname, String address, String regularHospital, String university, String gender, String date, String _phoneNo, String profileID, String uid) {
        DoctorID = doctorID;
        Pincode = pincode;
        Email = email;
        NationalIDCard = nationalIDCard;
        Fullname = fullname;
        Address = address;
        RegularHospital = regularHospital;
        University = university;
        Gender = gender;
        Date = date;
        this._phoneNo = _phoneNo;
        ProfileID = profileID;
        this.uid = uid;
    }

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNationalIDCard() {
        return NationalIDCard;
    }

    public void setNationalIDCard(String nationalIDCard) {
        NationalIDCard = nationalIDCard;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getRegularHospital() {
        return RegularHospital;
    }

    public void setRegularHospital(String regularHospital) {
        RegularHospital = regularHospital;
    }

    public String getUniversity() {
        return University;
    }

    public void setUniversity(String university) {
        University = university;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String get_phoneNo() {
        return _phoneNo;
    }

    public void set_phoneNo(String _phoneNo) {
        this._phoneNo = _phoneNo;
    }

    public String getProfileID() {
        return ProfileID;
    }

    public void setProfileID(String profileID) {
        ProfileID = profileID;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
