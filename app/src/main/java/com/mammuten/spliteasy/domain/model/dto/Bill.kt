package com.mammuten.spliteasy.domain.model.dto

data class Bill(
    val id: Int,
    val name: String,
    val groupId: Int,
    val amount: Double,
)