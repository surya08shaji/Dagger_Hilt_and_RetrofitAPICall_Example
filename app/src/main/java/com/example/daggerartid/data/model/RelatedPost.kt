package com.example.daggerartid.data.model

data class RelatedPost(
    val audio: Any,
    val author: String,
    val category: List<CategoryXXX>,
    val content: String,
    val copyright_text: String,
    val date: String,
    val favorite: Boolean,
    val id: Int,
    val image: String,
    val share_text: Any,
    val title: String,
    val url: String
)