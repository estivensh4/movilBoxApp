package io.github.estivensh4.movilboxapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.estivensh4.movilboxapp.data.local.dao.HistoryDao
import io.github.estivensh4.movilboxapp.data.local.dao.ProductsDao
import io.github.estivensh4.movilboxapp.data.local.entity.HistoryEntity
import io.github.estivensh4.movilboxapp.data.local.entity.ProductEntity

@Database(
    entities = [
        ProductEntity::class,
        HistoryEntity::class
    ],
    version = DATABASE_VERSION
)
@TypeConverters(Converters::class)
abstract class MovilBoxDatabase : RoomDatabase() {
    abstract val productsDao: ProductsDao
    abstract val historyDao: HistoryDao
}

// ROOM
const val TABLE_NAME_PRODUCTS = "products_entity"
const val TABLE_NAME_HISTORY = "history_entity"
const val DATABASE_PRODUCTS = "movilbox_db"
const val DATABASE_VERSION = 2
