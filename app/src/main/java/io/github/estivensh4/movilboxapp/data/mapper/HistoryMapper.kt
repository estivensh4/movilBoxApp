package io.github.estivensh4.movilboxapp.data.mapper

import io.github.estivensh4.movilboxapp.data.local.entity.HistoryEntity
import io.github.estivensh4.movilboxapp.domain.model.History

fun History.toEntity(): HistoryEntity {
    return HistoryEntity(
        id = id,
        historyList = historyList
    )
}

fun HistoryEntity?.toHistory(): History? {
    return if (this != null) {
        History(
            id = id,
            historyList = historyList
        )
    } else null
}