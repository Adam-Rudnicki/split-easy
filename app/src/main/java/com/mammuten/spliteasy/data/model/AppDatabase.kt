package com.mammuten.spliteasy.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.mammuten.spliteasy.data.model.entity.associativeentity.billusercrossref.BillUserCrossRefEntity
import com.mammuten.spliteasy.data.model.entity.associativeentity.groupusercrossref.GroupUserCrossRefEntity
import com.mammuten.spliteasy.data.model.entity.bill.BillEntity
import com.mammuten.spliteasy.data.model.entity.bill.BillDao
import com.mammuten.spliteasy.data.model.entity.contribution.ContributionEntity
import com.mammuten.spliteasy.data.model.entity.group.GroupEntity
import com.mammuten.spliteasy.data.model.entity.group.GroupDao
import com.mammuten.spliteasy.data.model.entity.user.MemberEntity
import com.mammuten.spliteasy.data.model.entity.user.MemberDao
import com.mammuten.spliteasy.data.util.Constants
import com.mammuten.spliteasy.data.converter.Converters

@Database(
    entities = [
        GroupEntity::class,
        BillEntity::class,
        MemberEntity::class,
        ContributionEntity::class,
        BillUserCrossRefEntity::class,
        GroupUserCrossRefEntity::class
               ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun billDao(): BillDao
    abstract fun userDao(): MemberDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, Constants.dbName
        ).build()
    }
}
