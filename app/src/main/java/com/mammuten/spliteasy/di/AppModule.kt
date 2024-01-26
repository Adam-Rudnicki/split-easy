package com.mammuten.spliteasy.di

import android.content.Context
import androidx.room.Room
import com.mammuten.spliteasy.data.repo.BillRepo
import com.mammuten.spliteasy.data.repo.ContributionRepo
import com.mammuten.spliteasy.data.repo.GeneralRepo
import com.mammuten.spliteasy.data.repo.GroupRepo
import com.mammuten.spliteasy.data.repo.MemberRepo
import com.mammuten.spliteasy.data.repo.UserRepo
import com.mammuten.spliteasy.data.source.local.LocalSplitDataSource
import com.mammuten.spliteasy.data.source.local.SplitDatabase
import com.mammuten.spliteasy.domain.usecase.bill.BillUseCases
import com.mammuten.spliteasy.domain.usecase.bill.DeleteBillUseCase
import com.mammuten.spliteasy.domain.usecase.bill.GetBillByIdUseCase
import com.mammuten.spliteasy.domain.usecase.bill.GetBillsByGroupIdUseCase
import com.mammuten.spliteasy.domain.usecase.bill.UpsertBillUseCase
import com.mammuten.spliteasy.domain.usecase.contribution.ContributionUseCases
import com.mammuten.spliteasy.domain.usecase.contribution.DeleteContributionUseCase
import com.mammuten.spliteasy.domain.usecase.contribution.GetContributionByBillIdAndMemberIdUseCase
import com.mammuten.spliteasy.domain.usecase.contribution.GetContributionsByBillIdUseCase
import com.mammuten.spliteasy.domain.usecase.contribution.UpdateContributionsUseCase
import com.mammuten.spliteasy.domain.usecase.contribution.UpsertContributionUseCase
import com.mammuten.spliteasy.domain.usecase.general.GeneralUseCases
import com.mammuten.spliteasy.domain.usecase.general.GetAllMembersInGroupAndContributionsInBillUseCase
import com.mammuten.spliteasy.domain.usecase.general.GetMembersAndContributionsInBillUseCase
import com.mammuten.spliteasy.domain.usecase.group.DeleteGroupUseCase
import com.mammuten.spliteasy.domain.usecase.group.GetGroupByIdUseCase
import com.mammuten.spliteasy.domain.usecase.group.GetGroupsUseCase
import com.mammuten.spliteasy.domain.usecase.group.GroupUseCases
import com.mammuten.spliteasy.domain.usecase.group.UpsertGroupUseCase
import com.mammuten.spliteasy.domain.usecase.member.DeleteMemberUseCase
import com.mammuten.spliteasy.domain.usecase.member.GetMemberByIdUseCase
import com.mammuten.spliteasy.domain.usecase.member.GetMembersByGroupIdUseCase
import com.mammuten.spliteasy.domain.usecase.member.MemberUseCases
import com.mammuten.spliteasy.domain.usecase.member.UpsertMemberUseCase
import com.mammuten.spliteasy.domain.usecase.member.UpsertMembersUseCase
import com.mammuten.spliteasy.domain.usecase.user.DeleteUserUseCase
import com.mammuten.spliteasy.domain.usecase.user.GetUserByIdUseCase
import com.mammuten.spliteasy.domain.usecase.user.GetUsersNotInGroupUseCase
import com.mammuten.spliteasy.domain.usecase.user.GetUsersUseCase
import com.mammuten.spliteasy.domain.usecase.user.UpsertUserUseCase
import com.mammuten.spliteasy.domain.usecase.user.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSplitDatabase(@ApplicationContext appContext: Context): SplitDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            SplitDatabase::class.java,
            SplitDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideLocalSplitDataSource(splitDatabase: SplitDatabase): LocalSplitDataSource {
        return LocalSplitDataSource(
            splitDatabase.groupDao,
            splitDatabase.billDao,
            splitDatabase.memberDao,
            splitDatabase.contributionDao,
            splitDatabase.userDao,
            splitDatabase.generalDao
        )
    }

    @Singleton
    @Provides
    fun provideGroupRepository(localSplitDataSource: LocalSplitDataSource): GroupRepo {
        return GroupRepo(localSplitDataSource)
    }

    @Singleton
    @Provides
    fun provideBillRepository(localSplitDataSource: LocalSplitDataSource): BillRepo {
        return BillRepo(localSplitDataSource)
    }

    @Singleton
    @Provides
    fun provideMemberRepository(localSplitDataSource: LocalSplitDataSource): MemberRepo {
        return MemberRepo(localSplitDataSource)
    }

    @Singleton
    @Provides
    fun provideContributionRepository(localSplitDataSource: LocalSplitDataSource): ContributionRepo {
        return ContributionRepo(localSplitDataSource)
    }

    @Singleton
    @Provides
    fun provideUserRepository(localSplitDataSource: LocalSplitDataSource): UserRepo {
        return UserRepo(localSplitDataSource)
    }

    @Singleton
    @Provides
    fun provideGeneralRepository(localSplitDataSource: LocalSplitDataSource): GeneralRepo {
        return GeneralRepo(localSplitDataSource)
    }

    @Singleton
    @Provides
    fun provideGroupUseCases(repo: GroupRepo): GroupUseCases {
        return GroupUseCases(
            upsertGroupUseCase = UpsertGroupUseCase(repo),
            deleteGroupUseCase = DeleteGroupUseCase(repo),
            getGroupByIdUseCase = GetGroupByIdUseCase(repo),
            getGroupsUseCase = GetGroupsUseCase(repo)
        )
    }

    @Singleton
    @Provides
    fun provideBillUseCases(repo: BillRepo): BillUseCases {
        return BillUseCases(
            upsertBillUseCase = UpsertBillUseCase(repo),
            deleteBillUseCase = DeleteBillUseCase(repo),
            getBillByIdUseCase = GetBillByIdUseCase(repo),
            getBillsByGroupIdUseCase = GetBillsByGroupIdUseCase(repo)
        )
    }

    @Singleton
    @Provides
    fun provideMemberUseCases(memberRepo: MemberRepo): MemberUseCases {
        return MemberUseCases(
            upsertMemberUseCase = UpsertMemberUseCase(memberRepo),
            upsertMembersUseCase = UpsertMembersUseCase(memberRepo),
            deleteMemberUseCase = DeleteMemberUseCase(memberRepo),
            getMemberByIdUseCase = GetMemberByIdUseCase(memberRepo),
            getMembersByGroupIdUseCase = GetMembersByGroupIdUseCase(memberRepo)
        )
    }

    @Singleton
    @Provides
    fun provideContributionUseCases(repo: ContributionRepo): ContributionUseCases {
        return ContributionUseCases(
            upsertContributionUseCase = UpsertContributionUseCase(repo),
            deleteContributionUseCase = DeleteContributionUseCase(repo),
            updateContributionsUseCase = UpdateContributionsUseCase(repo),
            getContributionsByBillIdUseCase = GetContributionsByBillIdUseCase(repo),
            getContributionByBillIdAndMemberIdUseCase = GetContributionByBillIdAndMemberIdUseCase(
                repo
            )
        )
    }

    @Singleton
    @Provides
    fun provideUserUseCases(repo: UserRepo): UserUseCases {
        return UserUseCases(
            upsertUserUseCase = UpsertUserUseCase(repo),
            deleteUserUseCase = DeleteUserUseCase(repo),
            getUserByIdUseCase = GetUserByIdUseCase(repo),
            getUsersUseCase = GetUsersUseCase(repo),
            getUsersNotInGroupUseCase = GetUsersNotInGroupUseCase(repo)
        )
    }

    @Singleton
    @Provides
    fun provideGeneralUseCases(repo: GeneralRepo): GeneralUseCases {
        return GeneralUseCases(
            getMembersAndContributionsInBillUseCase = GetMembersAndContributionsInBillUseCase(repo),
            getAllMembersInGroupAndContributionsInBillUseCase = GetAllMembersInGroupAndContributionsInBillUseCase(
                repo
            )
        )
    }
}