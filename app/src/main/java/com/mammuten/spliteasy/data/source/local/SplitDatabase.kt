package com.mammuten.spliteasy.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.mammuten.spliteasy.data.converter.Converters
import com.mammuten.spliteasy.data.source.local.entity.group.GroupEntity
import com.mammuten.spliteasy.data.source.local.entity.group.GroupDao
import com.mammuten.spliteasy.data.source.local.entity.bill.BillDao
import com.mammuten.spliteasy.data.source.local.entity.bill.BillEntity

@Database(
    entities = [
        GroupEntity::class,
        BillEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SplitDatabase : RoomDatabase() {
    abstract val groupDao: GroupDao
    abstract val billDao: BillDao

    companion object {
        const val DATABASE_NAME = "Split.db"
    }

}
