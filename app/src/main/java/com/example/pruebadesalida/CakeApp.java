package com.example.pruebadesalida;

import android.app.Application;

import androidx.room.Room;

public class CakeApp extends Application {
    public static DatabaseApp databaseApp;

    @Override
    public void onCreate() {
        super.onCreate();
        CakeApp.databaseApp = Room.databaseBuilder(this, DatabaseApp.class, "app-db").build();
    }
}
