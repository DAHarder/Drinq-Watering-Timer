package com.example.drinq.data.database;

import android.content.Context;

import com.example.drinq.data.dao.PlantDao;
import com.example.drinq.data.entity.PlantEntity;
import com.example.drinq.util.SampleData;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {PlantEntity.class}, version = 1, exportSchema = false)
public abstract class DrinqDatabase extends RoomDatabase {
    public abstract PlantDao plantDao();

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile DrinqDatabase INSTANCE;

    static DrinqDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (DrinqDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DrinqDatabase.class, "drinq_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            PlantDao mPlantDao = INSTANCE.plantDao();

            databaseWriteExecutor.execute(() -> {

                mPlantDao.deleteAllPlants();
                mPlantDao.insertAll(SampleData.getSamplePlantData());

            });
        }
    };
}
