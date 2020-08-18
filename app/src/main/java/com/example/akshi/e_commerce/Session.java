package com.example.akshi.e_commerce;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by akshi on 02-08-2017.
 */

public class Session {
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

public Session (Context context){

        sp = PreferenceManager.getDefaultSharedPreferences(context);
        }

public boolean setLoginUser(boolean status){

        spEditor = sp.edit();
        spEditor.putBoolean("is_logged_in", status);
        spEditor.commit();

        return true;
        }

public boolean getLoggedInUser(){

        return sp.getBoolean("is_logged_in", false);
        }
        }

