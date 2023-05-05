package com.example.practicaprueba.data.database.dao

import androidx.room.*
import com.example.practicaprueba.data.model.FacturaModel


@Dao
interface FacturaDao {

    @Query("SELECT * FROM table_facturas")
    suspend fun getFacturas(): List<FacturaModel>

    @Insert
    fun insert(facturas : List<FacturaModel>)

    @Query("Delete  FROM table_facturas")
    suspend fun deleteAllFacturas()
}
