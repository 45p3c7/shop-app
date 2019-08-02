package com.example.shopping.di

import android.content.Context
import androidx.room.Room
import com.example.shopping.data.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideDatabase(context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "App.db"
        )
                .fallbackToDestructiveMigration()
                .build()
    }
}