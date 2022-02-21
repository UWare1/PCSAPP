package com.example.pcs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_USERID = "userid";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DATE = "age";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_PROFILEID = "profileid";
    public static final String KEY_NATIONALIDCARD = "nationalidcard";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_ALLERGY = "allergy";
    public static final String KEY_MEDICAL = "medical";

    public SessionManager(Context _context) {
        context = _context;
        userSession = context.getSharedPreferences("userLoginSession",Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createLoginSession(String fullname, String userid, String email, String phoneNo, String password, String age, String gender, String profileid, String nationalidcard, String address, String allergy, String medical){
        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_FULLNAME,fullname);
        editor.putString(KEY_USERID,userid);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PHONENUMBER,phoneNo);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_DATE,age);
        editor.putString(KEY_GENDER,gender);
        editor.putString(KEY_PROFILEID,profileid);
        editor.putString(KEY_NATIONALIDCARD,nationalidcard);
        editor.putString(KEY_ADDRESS,address);
        editor.putString(KEY_ALLERGY,allergy);
        editor.putString(KEY_MEDICAL,medical);

        editor.commit();
    }

    public HashMap<String, String> getUserDatailFromSession(){
        HashMap<String,String> userData = new HashMap<String,String>();

        userData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME,null));
        userData.put(KEY_USERID, userSession.getString(KEY_USERID,null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL,null));
        userData.put(KEY_PHONENUMBER, userSession.getString(KEY_PHONENUMBER,null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD,null));
        userData.put(KEY_DATE, userSession.getString(KEY_DATE,null));
        userData.put(KEY_GENDER, userSession.getString(KEY_GENDER,null));
        userData.put(KEY_PROFILEID, userSession.getString(KEY_PROFILEID,null));
        userData.put(KEY_NATIONALIDCARD, userSession.getString(KEY_NATIONALIDCARD,null));
        userData.put(KEY_ADDRESS, userSession.getString(KEY_ADDRESS,null));
        userData.put(KEY_ALLERGY, userSession.getString(KEY_ALLERGY,null));
        userData.put(KEY_MEDICAL, userSession.getString(KEY_MEDICAL,null));

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