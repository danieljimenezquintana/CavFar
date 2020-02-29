package com.example.cavfar.Interfaz;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cavfar.Users.Usuario;
import com.example.cavfar.Users.loginResponse;

import java.util.ArrayList;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx){
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx){
        if(mInstance == null){
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    public void saveUser(loginResponse response){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("email", response.getUser().getEmail());
        editor.putString("nombre", response.getUser().getNombre());
        editor.putString("usuario", response.getUser().getUsuario());
        editor.putString("token", response.getAccess_token());

        editor.apply();
    }
    /*public ArrayList showAllModels(){
        SharedPreferences sharedPreferences =  mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        ArrayList models = (ArrayList) sharedPreferences.getAll();
        return models;
    };*/

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("token",null) != null;
    }

    public Usuario getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new Usuario(
          sharedPreferences.getString("name",null),
          sharedPreferences.getString("email",null),
          sharedPreferences.getString("usuario",null),
          sharedPreferences.getString("token",null)
        );
    }

    public void clear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}