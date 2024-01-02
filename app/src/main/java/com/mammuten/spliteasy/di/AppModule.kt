package com.mammuten.spliteasy.di

import android.content.Context
import androidx.room.Room
import com.mammuten.spliteasy.data.repo.GroupRepo
import com.mammuten.spliteasy.data.source.local.LocalSplitDataSource
import com.mammuten.spliteasy.data.source.local.SplitDatabase
import com.mammuten.spliteasy.domain.usecase.group.DeleteGroupUseCase
import com.mammuten.spliteasy.domain.usecase.group.GetGroupByIdUseCase
import com.mammuten.spliteasy.domain.usecase.group.GetGroupsUseCase
import com.mammuten.spliteasy.domain.usecase.group.GroupUseCases
import com.mammuten.spliteasy.domain.usecase.group.UpsertGroupUseCase
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
        return LocalSplitDataSource(splitDatabase.groupDao)
    }

    @Singleton
    @Provides
    fun provideGroupRepository(localSplitDataSource: LocalSplitDataSource): GroupRepo {
        return GroupRepo(localSplitDataSource)
    }

    @Singleton
    @Provides
    fun provideNoteUseCases(repo: GroupRepo): GroupUseCases {
        return GroupUseCases(
            upsertGroupUseCase = UpsertGroupUseCase(repo),
            deleteGroupUseCase = DeleteGroupUseCase(repo),
            getGroupByIdUseCase = GetGroupByIdUseCase(repo),
            getGroups = GetGroupsUseCase(repo)
        )
    }
}