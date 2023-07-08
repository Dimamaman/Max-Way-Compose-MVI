package uz.gita.dimaa.mymaxway.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.dimaa.mymaxway.data.local.room.dao.FoodDao
import uz.gita.dimaa.mymaxway.data.local.room.database.FoodDatabase
import uz.gita.dimaa.mymaxway.domain.model.FoodData
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): FoodDatabase = Room.databaseBuilder(
        context,
        FoodDatabase::class.java, "awqat.db"
    ).allowMainThreadQueries().build()

    @[Provides Singleton]
    fun provideFoodDao(db: FoodDatabase): FoodDao = db.addFood()
}