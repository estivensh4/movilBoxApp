package io.github.estivensh4.movilboxapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.estivensh4.movilboxapp.data.local.TABLE_NAME_HISTORY
import io.github.estivensh4.movilboxapp.data.local.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(historyEntity: HistoryEntity)

    @Query("SELECT * FROM $TABLE_NAME_HISTORY")
    fun getHistory(): Flow<HistoryEntity?>
}
