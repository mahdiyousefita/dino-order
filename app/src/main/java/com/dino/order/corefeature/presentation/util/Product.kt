package com.dino.order.corefeature.presentation.util

data class Product(
    val name: String,
    val description: String,
    val url: String? = null,
    val isImage: Boolean
)