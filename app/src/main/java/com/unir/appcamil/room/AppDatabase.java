package com.unir.appcamil.room;

import android.content.Context;

import androidx.room3.Database;
import androidx.room3.Room;
import androidx.room3.RoomDatabase;

@Database(entities = {Review.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ReviewDAO reviewDAO();
    private static volatile AppDatabase INSTANCIA;
    public static AppDatabase obterInstancia(Context contexto) {
        if (INSTANCIA == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(
                            contexto.getApplicationContext(),
                            AppDatabase.class, "banco_reviews"
                    ).allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCIA;
    }
}
