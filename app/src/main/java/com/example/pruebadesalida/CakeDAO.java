package com.example.pruebadesalida;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CakeDAO {
    // DAO para manipular la tabla cake_entity
    @Insert
    public void addCake(CakeEntity cakeEntity);

    @Query("SELECT * from cake_entity")
    public List<CakeEntity> getAllCakes();

    @Query("SELECT * from cake_entity WHERE id=:id")
    public CakeEntity getCakeById(int id);

    // DAO para manipular la tabla cake_detail_entity
    @Insert
    public void addCakeDetails(CakeDetailEntity cakeDetailEntity);

    @Query("SELECT * from cake_detail_entity WHERE id=:id")
    public CakeDetailEntity getCakeDetailsById(int id);
}
