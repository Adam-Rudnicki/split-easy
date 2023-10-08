package com.mammuten.spliteasy.data.mapper

interface Mapper<E, D> {
    fun toDomain(entity: E): D
    fun toEntity(domain: D): E

    fun toDomainList(entities: List<E>): List<D> {
        return entities.map { toDomain(it) }
    }

    fun toEntityList(domains: List<D>): List<E> {
        return domains.map { toEntity(it) }
    }
}