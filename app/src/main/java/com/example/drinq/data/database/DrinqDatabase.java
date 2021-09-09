package com.example.drinq.data.database;

import android.content.Context;

import com.example.drinq.data.dao.PlantDao;
import com.example.drinq.data.dao.ReportDao;
import com.example.drinq.data.entity.PlantEntity;
import com.example.drinq.data.entity.ReportEntity;
import com.example.drinq.util.SampleData;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Used for connection and instantiation of the Database
 */
@androidx.room.Database(entities = {PlantEntity.class, ReportEntity.class}, version = 5, exportSchema = false)
public abstract class DrinqDatabase extends RoomDatabase {

    private static volatile DrinqDatabase INSTANCE;

    public abstract PlantDao plantDao();
    public abstract ReportDao reportDao();

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static synchronized DrinqDatabase getDatabase(final Context context) {
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

    /**
     * Call back used for pre-loading data into the database for testing
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            PlantDao mPlantDao = INSTANCE.plantDao();
            ReportDao mReportDao = INSTANCE.reportDao();

            databaseWriteExecutor.execute(() -> {

                mPlantDao.deleteAllPlants();
                mPlantDao.insertAll(SampleData.getSamplePlantData());

                mReportDao.deleteAllPlants();
                mReportDao.insertAll(SampleData.getSampleReportData());

            });
        }
    };
}
