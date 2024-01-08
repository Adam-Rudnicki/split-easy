package com.mammuten.spliteasy.data.source.local

import com.mammuten.spliteasy.data.source.local.entity.bill.BillDao
import com.mammuten.spliteasy.data.source.local.entity.bill.BillEntity
import com.mammuten.spliteasy.data.source.local.entity.contribution.ContributionDao
import com.mammuten.spliteasy.data.source.local.entity.contribution.ContributionEntity
import com.mammuten.spliteasy.data.source.local.entity.group.GroupDao
import com.mammuten.spliteasy.data.source.local.entity.group.GroupEntity
import com.mammuten.spliteasy.data.source.local.entity.member.MemberDao
import com.mammuten.spliteasy.data.source.local.entity.member.MemberEntity
import kotlinx.coroutines.flow.Flow

class LocalSplitDataSource(
    private val groupDao: GroupDao,
    private val billDao: BillDao,
    private val memberDao: MemberDao,
    private val contributionDao: ContributionDao
) {
    suspend fun upsertGroup(groupEntity: GroupEntity) = groupDao.upsertGroup(groupEntity)
    suspend fun deleteGroup(groupEntity: GroupEntity) = groupDao.deleteGroup(groupEntity)
    fun getGroupById(groupId: Int): Flow<GroupEntity?> = groupDao.getGroupById(groupId)
    fun getGroups(): Flow<List<GroupEntity>> = groupDao.getGroups()

    suspend fun upsertBill(billEntity: BillEntity) = billDao.upsertBill(billEntity)
    suspend fun deleteBill(billEntity: BillEntity) = billDao.deleteBill(billEntity)
    fun getBillById(billId: Int): Flow<BillEntity?> = billDao.getBillById(billId)
    fun getBillsByGroupId(groupId: Int): Flow<List<BillEntity>> = billDao.getBillsByGroupId(groupId)

    suspend fun upsertMember(memberEntity: MemberEntity) = memberDao.upsertMember(memberEntity)
    suspend fun deleteMember(memberEntity: MemberEntity) = memberDao.deleteMember(memberEntity)
    fun getMemberById(memberId: Int): Flow<MemberEntity?> = memberDao.getMemberById(memberId)
    fun getMembersByGroupId(groupId: Int): Flow<List<MemberEntity>> =
        memberDao.getMembersByGroupId(groupId)

    suspend fun upsertContribution(contributionEntity: ContributionEntity) =
        contributionDao.upsertContribution(contributionEntity)

    suspend fun deleteContribution(contributionEntity: ContributionEntity) =
        contributionDao.deleteContribution(contributionEntity)

    fun getContributionByBillIdAndMemberId(billId: Int, memberId: Int): Flow<ContributionEntity?> =
        contributionDao.getContributionByBillIdAndMemberId(billId, memberId)

    fun getContributionsByBillId(billId: Int): Flow<List<ContributionEntity>> =
        contributionDao.getContributionsByBillId(billId)

    fun getContributionsByMemberId(memberId: Int): Flow<List<ContributionEntity>> =
        contributionDao.getContributionsByMemberId(memberId)
}