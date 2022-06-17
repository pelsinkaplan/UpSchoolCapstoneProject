package com.pelsinkaplan.upschoolcapstoneproject.service.room

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pelsinkaplan.upschoolcapstoneproject.data.model.ProductChart

@Database(
    entities = [ProductChart::class],
    version = 1
)
abstract class ChartDatabase : RoomDatabase() {

    abstract fun getChartDao(): ChartDao

    companion object {
        private var instance: ChartDatabase? = null

        fun getChartDatabase(context: Context): ChartDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    ChartDatabase::class.java,
                    "chart.db"
                ).allowMainThreadQueries().build()
            }
            return instance
        }
    }
}