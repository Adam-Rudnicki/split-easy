package com.mammuten.spliteasy.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.mammuten.spliteasy.data.converter.Converters
import com.mammuten.spliteasy.data.source.local.entity.group.GroupEntity
import com.mammuten.spliteasy.data.source.local.entity.group.GroupDao
import com.mammuten.spliteasy.data.source.local.entity.bill.BillDao
import com.mammuten.spliteasy.data.source.local.entity.bill.BillEntity
import com.mammuten.spliteasy.data.source.local.entity.member.MemberDao
import com.mammuten.spliteasy.data.source.local.entity.member.MemberEntity
import com.mammuten.spliteasy.data.source.local.entity.contribution.ContributionDao
import com.mammuten.spliteasy.data.source.local.entity.contribution.ContributionEntity

@Database(
    entities = [
        GroupEntity::class,
        BillEntity::class,
        MemberEntity::class,
        ContributionEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SplitDatabase : RoomDatabase() {
    abstract val groupDao: GroupDao
    abstract val billDao: BillDao
    abstract val memberDao: MemberDao
    abstract val contributionDao: ContributionDao

    companion object {
        const val DATABASE_NAME = "Split.db"
    }
}
