package io.github.estivensh4.movilboxapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.estivensh4.movilboxapp.data.local.TABLE_NAME_HISTORY

@Entity(tableName = TABLE_NAME_HISTORY)
data class HistoryEntity(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    val historyList: List<String> = emptyList()
)
