package com.example.kotlinfinals

// this class represents the data model for a book

data class BookData(
    val bookName: String? = null,
    val bookGenre: String? = null,
    val bookPublisher: String? = null,
    val bookNumber: String? = null
)