package io.github.estivensh4.movilboxapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.estivensh4.movilboxapp.data.local.TABLE_NAME_PRODUCTS

@Entity(tableName = TABLE_NAME_PRODUCTS)
data class ProductEntity(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val price: Int = 0,
    val discountPercentage: Double = 0.0,
    val rating: Double = 0.0,
    val stock: Int = 0,
    val brand: String = "",
    val category: String = "",
    val thumbnail: String = "",
    val images: List<String> = emptyList()
)
