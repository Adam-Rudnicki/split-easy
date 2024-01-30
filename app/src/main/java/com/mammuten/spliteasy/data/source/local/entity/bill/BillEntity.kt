package com.mammuten.spliteasy.data.source.local.entity.bill

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import com.mammuten.spliteasy.data.source.local.entity.group.GroupEntity
import java.util.Date

@Entity(
    tableName = BillEntity.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["groupId"]
        )
    ]
)
data class BillEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    val groupId: Int,
    val name: String,
    val description: String? = null,
    val amount: Double? = null,
    val date: Date? = null
) {
    companion object {
        const val TABLE_NAME = "bills"
    }
}