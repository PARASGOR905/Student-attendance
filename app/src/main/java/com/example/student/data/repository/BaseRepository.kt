package com.example.student.data.repository

import kotlinx.coroutines.flow.Flow

/**
 * Base repository interface that defines common CRUD operations.
 */
interface BaseRepository<T, ID> {
    suspend fun insert(item: T): Long
    suspend fun update(item: T)
    suspend fun delete(item: T)
    suspend fun getById(id: ID): T?
    fun getAll(): Flow<List<T>>
}
