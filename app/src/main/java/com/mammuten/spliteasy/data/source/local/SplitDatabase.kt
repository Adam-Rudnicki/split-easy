package com.mammuten.spliteasy.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.mammuten.spliteasy.data.source.local.entity.group.GroupEntity
import com.mammuten.spliteasy.data.source.local.entity.group.GroupDao
import com.mammuten.spliteasy.data.converter.Converters

@Database(
    entities = [GroupEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SplitDatabase : RoomDatabase() {
    abstract val groupDao: GroupDao

    companion object{
        const val DATABASE_NAME = "Split.db"
    }

}
