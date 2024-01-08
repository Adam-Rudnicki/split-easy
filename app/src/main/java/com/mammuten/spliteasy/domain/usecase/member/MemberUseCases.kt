package com.mammuten.spliteasy.domain.usecase.member

data class MemberUseCases(
    val upsertMemberUseCase: UpsertMemberUseCase,
    val deleteMemberUseCase: DeleteMemberUseCase,
    val getMemberByIdUseCase: GetMemberByIdUseCase,
    val getMembersByGroupIdUseCase: GetMembersByGroupIdUseCase
)