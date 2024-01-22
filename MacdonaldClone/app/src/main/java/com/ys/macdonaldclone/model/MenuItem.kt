package com.ys.macdonaldclone.model

data class MenuItem(
    val id: Long,
    val name: String,
    val description: String,
    val image: String,
    val price: Double,
    val categoryId: Long,
    val quantity: Int = 0,
)