package com.nba.guide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SessionManager {

    public static final String USER_ID = "user_id";

    public static final String KEY_TOKEN = "token";

    private static final String PREF_NAME = "athleteguide";

    private static final String GENDER = "gender";

    private static final String IS_LOGIN = "isLogin";

    private static final String NAME = "name";

    private static final String AGE = "age";

    private static final String REG_ID = "reg_id";

    private static final String List_Size = "list";

    private static final String Email_ID = "email";

    private static final String Password = "Password";


    SharedPreferences pref;

    Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(String userid) {

        editor.putString(USER_ID, userid);

        editor.commit();
    }

    public void setList_Size(String list_Size) {

        editor.putString(List_Size, list_Size);

        editor.commit();
    }

    public void setEmail_ID(String email_id) {

        editor.putString(Email_ID, email_id);

        editor.commit();
    }

    public void setPassword(String password) {

        editor.putString(Password, password);

        editor.commit();
    }


    public String getUserId() {
        return pref.getString(USER_ID, null);
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(IS_LOGIN, isLoggedIn);

        // commit changes
        editor.commit();


    }

    public void setRegId(String reg_id){
        editor.putString(REG_ID, reg_id);
        editor.commit();
    }
    public String getRegId() {
        return pref.getString(REG_ID, null);
    }


    public String getEmail() {
        return pref.getString(Email_ID, null);
    }

    public String getPassword() {
        return pref.getString(Password, null);
    }

    public String getList_Size() {
        return pref.getString(List_Size, null);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }


    public void setName(String name){
        editor.putString(NAME, name);
        editor.commit();
    }

    public String getName() {
        return pref.getString(NAME, null);
    }

    public void logoutUser() {

        editor.remove(USER_ID);

        editor.remove(GENDER);

        editor.remove(NAME);

        editor.remove(AGE);

        editor.remove(PREF_NAME);

        editor.remove(IS_LOGIN);

        editor.remove(KEY_TOKEN);

        editor.commit();

        System.out.println("logout SuccessFull");
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        _context.startActivity(i);
    }

    public void destroy() {
        editor.clear();
        editor.commit();
    }


}