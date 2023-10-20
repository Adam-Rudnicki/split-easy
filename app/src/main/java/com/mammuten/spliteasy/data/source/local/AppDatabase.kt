package com.mammuten.spliteasy.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.mammuten.spliteasy.data.source.local.model.entity.bill.BillEntity
import com.mammuten.spliteasy.data.source.local.model.entity.bill.BillDao
import com.mammuten.spliteasy.data.source.local.model.entity.contribution.ContributionEntity
import com.mammuten.spliteasy.data.source.local.model.entity.contribution.ContributionDao
import com.mammuten.spliteasy.data.source.local.model.entity.group.GroupEntity
import com.mammuten.spliteasy.data.source.local.model.entity.group.GroupDao
import com.mammuten.spliteasy.data.source.local.model.entity.member.MemberEntity
import com.mammuten.spliteasy.data.source.local.model.entity.member.MemberDao

import com.mammuten.spliteasy.data.converter.Converters
import com.mammuten.spliteasy.data.source.local.model.relation.onetomany.billwithcontributions.BillWithContributionsDao
import com.mammuten.spliteasy.data.source.local.model.relation.onetomany.groupwithbills.GroupWithBillsDao
import com.mammuten.spliteasy.data.source.local.model.relation.onetomany.groupwithmembers.GroupWithMembersDao
import com.mammuten.spliteasy.data.source.local.model.relation.onetomany.memberwithcontributions.MemberWithContributionsDao
import com.mammuten.spliteasy.data.util.Constants

@Database(
    entities = [
        GroupEntity::class,
        BillEntity::class,
        MemberEntity::class,
        ContributionEntity::class,
               ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun billDao(): BillDao
    abstract fun memberDao(): MemberDao
    abstract fun contributionDao(): ContributionDao

    abstract fun groupWithBillsDao(): GroupWithBillsDao

    abstract fun groupWithMembersDao(): GroupWithMembersDao

    abstract fun billWithContributionsDao(): BillWithContributionsDao

    abstract fun memberWithContributionsDao(): MemberWithContributionsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, Constants.localDbName
        ).build()
    }
}
