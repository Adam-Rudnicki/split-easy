package com.mammuten.spliteasy.data.mapper

import com.mammuten.spliteasy.data.model.entity.bill.BillEntity
import com.mammuten.spliteasy.domain.model.Bill

class BillMapper: Mapper<BillEntity, Bill>{
    override fun toDomain(entity: BillEntity): Bill {
        return Bill(
            id = entity.id,
            name = entity.name,
            group = entity.group,
            amount = entity.amount,
            contributions = entity.contributions,
            participants = entity.participants
        )
    }

    override fun toEntity(domain: Bill): BillEntity {
        return BillEntity(
            id = domain.id,
            name = domain.name,
            group = domain.group,
            amount = domain.amount,
            contributions = domain.contributions,
            participants = domain.participants
        )
    }
}