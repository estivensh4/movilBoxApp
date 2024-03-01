package io.github.estivensh4.movilboxapp.util

enum class FilterList {
    PRICE,
    DISCOUNT,
    CATEGORY,
    RATING,
    STOCK,
    BRAND,
    NOTHING;

    companion object {
        fun getList() = FilterList.entries.toList().filter { it != NOTHING }
        fun getValue(value: String): FilterList {
            return FilterList.valueOf(value)
        }
    }
}