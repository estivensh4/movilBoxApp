package io.github.estivensh4.movilboxapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.estivensh4.movilboxapp.domain.model.Product

class Converters {

    @TypeConverter
    fun toListResult(data: String): List<Product> {
        val resultList = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(data, resultList)
    }

    @TypeConverter
    fun fromResultList(list: List<Product>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toListString(data: String): List<String> {
        val resultList = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(data, resultList)
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return Gson().toJson(list)
    }
}
