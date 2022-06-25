package com.pelsinkaplan.upschoolcapstoneproject.service.room

/**
 * Created by Pelşin KAPLAN on 19.06.2022.
 */
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product

@Database(
    entities = [Product::class],
    version = 1
)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun getFavoritesDao(): FavoritesDao

    companion object {
        private var instance: FavoritesDatabase? = null

        fun getFavoritesDatabase(context: Context): FavoritesDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    FavoritesDatabase::class.java,
                    "favoriteProducts.db"
                ).allowMainThreadQueries().build()
            }
            return instance
        }
    }
}