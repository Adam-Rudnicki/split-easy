package com.mammuten.spliteasy.data.model.relation.manytomany.billwithusers

import androidx.room.Query
import androidx.room.Transaction

interface BillWithUsersDao {
    @Transaction
    @Query("SELECT * FROM bills")
    fun getBillsWithUsers(): List<BillWithUsers>
}