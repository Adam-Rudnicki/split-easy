package com.mammuten.spliteasy.data.model

import androidx.room.Database
import androidx.room.RoomDatabase

import com.mammuten.spliteasy.data.model.entity.bill.Bill
import com.mammuten.spliteasy.data.model.entity.bill.BillDao
import com.mammuten.spliteasy.data.model.entity.group.Group
import com.mammuten.spliteasy.data.model.entity.group.GroupDao
import com.mammuten.spliteasy.data.model.entity.user.User
import com.mammuten.spliteasy.data.model.entity.user.UserDao

@Database(
    entities = [
        Group::class,
        Bill::class,
        User::class
               ],
    views = [],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun billDao(): BillDao
    abstract fun userDao(): UserDao
}
