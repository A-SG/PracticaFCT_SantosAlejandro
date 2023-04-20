package com.example.practicaprueba.di

import android.content.Context
import androidx.room.Room
import com.example.practicaprueba.data.database.FacturasDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    const val FACTURA_DATABASE_NAME = "factura_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(context, FacturasDatabase::class.java, FACTURA_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideNumeroFacturaDao(db: FacturasDatabase) = db.getFacturas()
}

