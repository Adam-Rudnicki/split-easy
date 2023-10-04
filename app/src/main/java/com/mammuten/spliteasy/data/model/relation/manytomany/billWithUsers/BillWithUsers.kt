package com.mammuten.spliteasy.data.model.relation.manytomany.billWithUsers

import com.mammuten.spliteasy.data.model.entity.bill.Bill
import com.mammuten.spliteasy.data.model.entity.user.User

data class BillWithUsers (
    val bill: Bill,
    val users: List<User>
)