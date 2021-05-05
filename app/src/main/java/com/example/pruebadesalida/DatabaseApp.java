package com.example.pruebadesalida;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CakeEntity.class,CakeDetailEntity.class}, version = 1)
public abstract class DatabaseApp extends RoomDatabase {
    public abstract CakeDAO cakeDAO();
}
