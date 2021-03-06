package com.example.pcs;

public class UserHelperClass {

    String UserID, Password, Email, NationalIDCard,
            Fullname, Address, Medical, Allergy, Gender, Date, _phoneNo,
            ProfileID;

    public UserHelperClass(){}

    public UserHelperClass(String userID, String password, String email, String nationalIDCard, String fullname, String address, String medical, String allergy, String gender, String date, String _phoneNo, String profileID) {
        UserID = userID;
        Password = password;
        Email = email;
        NationalIDCard = nationalIDCard;
        Fullname = fullname;
        Address = address;
        Medical = medical;
        Allergy = allergy;
        Gender = gender;
        Date = date;
        this._phoneNo = _phoneNo;
        ProfileID = profileID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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

    public String getMedical() {
        return Medical;
    }

    public void setMedical(String medical) {
        Medical = medical;
    }

    public String getAllergy() {
        return Allergy;
    }

    public void setAllergy(String allergy) {
        Allergy = allergy;
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
}
