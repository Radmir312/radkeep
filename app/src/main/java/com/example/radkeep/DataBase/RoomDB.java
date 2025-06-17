package com.example.radkeep.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.radkeep.Models.Notes;
import com.example.radkeep.Models.User;

@Database(entities = {Notes.class, User.class}, version = 2, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB database;
    private static final String DATABASE_NAME = "NoteApp";


    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            database.execSQL("CREATE TABLE IF NOT EXISTS `users` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`email` TEXT, " +
                    "`password` TEXT, " +
                    "`name` TEXT)");


            try {
                database.execSQL("ALTER TABLE notes ADD COLUMN userId INTEGER");
            } catch (Exception e) {

            }
        }
    };

    public synchronized static RoomDB getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDB.class, DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract mainDAO mainDAO();
}