package com.example.pcs;

public class UserHelperClass {

    String UserID, Password, Email, NationalIDCard,
            Fullname, Address, Medical, Allergy, _phoneNo;

    public UserHelperClass(){}

    public UserHelperClass(String userID, String password, String email, String nationalIDCard, String fullname, String address, String medical, String allergy, String _phoneNo) {
        UserID = userID;
        Password = password;
        Email = email;
        NationalIDCard = nationalIDCard;
        Fullname = fullname;
        Address = address;
        Medical = medical;
        Allergy = allergy;
        this._phoneNo = _phoneNo;
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

    public String get_phoneNo() {
        return _phoneNo;
    }

    public void set_phoneNo(String _phoneNo) {
        this._phoneNo = _phoneNo;
    }
}
