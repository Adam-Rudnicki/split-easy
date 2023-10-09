package com.mammuten.spliteasy.data.mapper

import com.mammuten.spliteasy.data.model.entity.bill.BillEntity
import com.mammuten.spliteasy.domain.model.Bill

class BillMapper: Mapper<BillEntity, Bill>{
    override fun toDomain(entity: BillEntity): Bill {
        return Bill(
            id = entity.id,
            name = entity.name,
            groupId = entity.groupId,
            amount = entity.amount,
        )
    }

    override fun toEntity(domain: Bill): BillEntity {
        return BillEntity(
            id = domain.id,
            name = domain.name,
            groupId = domain.groupId,
            amount = domain.amount,
        )
    }
}