package com.mammuten.spliteasy.data.model.entity.contribution

import androidx.room.Entity

@Entity(tableName = "contributions")
data class Contribution(
    val billId: Int,
    val userId: Int,
    val amountPaid: Double,
    val amountOwed: Double,
)