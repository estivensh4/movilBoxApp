package io.github.estivensh4.movilboxapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.estivensh4.movilboxapp.data.local.TABLE_NAME_PRODUCTS
import io.github.estivensh4.movilboxapp.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleProduct(productEntity: ProductEntity)

    @Query("DELETE FROM $TABLE_NAME_PRODUCTS WHERE id = :id")
    suspend fun deleteProductById(id: Int)

    @Query("SELECT * FROM $TABLE_NAME_PRODUCTS WHERE id = :id")
    fun getSingleProduct(id: Int): Flow<ProductEntity?>
}
