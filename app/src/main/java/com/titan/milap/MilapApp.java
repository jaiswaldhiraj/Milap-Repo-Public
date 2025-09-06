package com.titan.milap;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.FirebaseApp;


public class MilapApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Firebase is initialized once when the app starts
        FirebaseApp.initializeApp(this);


        // Force light mode for the entire app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}