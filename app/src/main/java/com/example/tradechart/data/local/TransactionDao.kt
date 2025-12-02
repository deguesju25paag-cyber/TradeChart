package com.example.tradechart.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tradechart.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY timestamp DESC")
    fun getTransactionsForUser(userId: Long): Flow<List<Transaction>>
}
