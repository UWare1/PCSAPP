package com.example.pcs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManagerDoctor {
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_DOCTORID = "doctorid";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_PINCODE = "pincode";
    public static final String KEY_DATE = "age";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_PROFILEID = "profileid";
    public static final String KEY_NATIONALIDCARD = "nationalidcard";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_REGULARHOSPITAL = "regularhospital";
    public static final String KEY_UNIVERSITY = "university";
    public static final String KEY_UID = "uid";

    public SessionManagerDoctor(Context _context) {
        context = _context;
        userSession = context.getSharedPreferences("userLoginSession",Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createLoginSession(String fullname, String doctorid, String email, String phoneNo, String pincode, String age, String gender, String profileid, String nationalidcard, String address, String regularhospital, String university, String uid){
        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_FULLNAME,fullname);
        editor.putString(KEY_DOCTORID,doctorid);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PHONENUMBER,phoneNo);
        editor.putString(KEY_PINCODE,pincode);
        editor.putString(KEY_DATE,age);
        editor.putString(KEY_GENDER,gender);
        editor.putString(KEY_PROFILEID,profileid);
        editor.putString(KEY_NATIONALIDCARD,nationalidcard);
        editor.putString(KEY_ADDRESS,address);
        editor.putString(KEY_REGULARHOSPITAL,regularhospital);
        editor.putString(KEY_UNIVERSITY,university);
        editor.putString(KEY_UID,uid);

        editor.commit();
    }

    public HashMap<String, String> getUserDatailFromSession(){
        HashMap<String,String> userData = new HashMap<String,String>();

        userData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME,null));
        userData.put(KEY_DOCTORID, userSession.getString(KEY_DOCTORID,null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL,null));
        userData.put(KEY_PHONENUMBER, userSession.getString(KEY_PHONENUMBER,null));
        userData.put(KEY_PINCODE, userSession.getString(KEY_PINCODE,null));
        userData.put(KEY_DATE, userSession.getString(KEY_DATE,null));
        userData.put(KEY_GENDER, userSession.getString(KEY_GENDER,null));
        userData.put(KEY_PROFILEID, userSession.getString(KEY_PROFILEID,null));
        userData.put(KEY_NATIONALIDCARD, userSession.getString(KEY_NATIONALIDCARD,null));
        userData.put(KEY_ADDRESS, userSession.getString(KEY_ADDRESS,null));
        userData.put(KEY_REGULARHOSPITAL, userSession.getString(KEY_REGULARHOSPITAL,null));
        userData.put(KEY_UNIVERSITY, userSession.getString(KEY_UNIVERSITY,null));
        userData.put(KEY_UID, userSession.getString(KEY_UID,null));

        return  userData;
    }

    public boolean checkLogin(){
        if(userSession.getBoolean(IS_LOGIN, false)){
            return true;
        }
        else
            return  false;
    }

    public void logoutUserFromSession(){
        editor.clear();
        editor.commit();
    }

}
