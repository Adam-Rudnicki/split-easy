package com.mammuten.spliteasy.data.source.local

import com.mammuten.spliteasy.data.source.local.entity.bill.BillDao
import com.mammuten.spliteasy.data.source.local.entity.bill.BillEntity
import com.mammuten.spliteasy.data.source.local.entity.group.GroupDao
import com.mammuten.spliteasy.data.source.local.entity.group.GroupEntity
import kotlinx.coroutines.flow.Flow

class LocalSplitDataSource(
    private val groupDao: GroupDao,
    private val billDao: BillDao
) {
    suspend fun upsertGroup(groupEntity: GroupEntity) = groupDao.upsertGroup(groupEntity)
    suspend fun deleteGroup(groupEntity: GroupEntity) = groupDao.deleteGroup(groupEntity)
    fun getGroupById(groupId: Int): Flow<GroupEntity?> = groupDao.getGroupById(groupId)
    fun getGroups(): Flow<List<GroupEntity>> = groupDao.getGroups()

    suspend fun upsertBill(billEntity: BillEntity) = billDao.upsertBill(billEntity)
    suspend fun deleteBill(billEntity: BillEntity) = billDao.deleteBill(billEntity)
    fun getBillById(billId: Int): Flow<BillEntity?> = billDao.getBillById(billId)
    fun getBillsByGroupId(groupId: Int): Flow<List<BillEntity>> = billDao.getBillsByGroupId(groupId)
}