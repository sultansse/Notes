package com.software1t.notes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, NoteDatabase::class.java, "notes")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance as NoteDatabase
        }
    }

}

//        fun getInstance(context: Context): NoteDatabase {
//            return instance ?: synchronized(this) {
//                instance ?: buildDatabase(context).also { instance = it }
//            }
//        }
//
//        private fun buildDatabase(context: Context): NoteDatabase {
//            return Room.databaseBuilder(context, NoteDatabase::class.java, "notes.db")
//                .addCallback(object : Callback() {
//                    override fun onCreate(db: SupportSQLiteDatabase) {
//                        super.onCreate(db)
//                        db.execSQL("INSERT INTO notes (title, description) VALUES ('Note 1', 'Description 1')")
//                        db.execSQL("INSERT INTO notes (title, description) VALUES ('Note 2', 'Description 2')")
//
//                        // Add more insert statements for more data
//                    }
//                })
//                .build()
//        }
//    }