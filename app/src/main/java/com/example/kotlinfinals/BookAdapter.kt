package com.example.kotlinfinals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(private var bookList: List<BookData>) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookName: TextView = itemView.findViewById(R.id.bookName)
        val bookGenre: TextView = itemView.findViewById(R.id.bookGenre)
        val bookPublisher: TextView = itemView.findViewById(R.id.bookPublisher)
        val bookNumber: TextView = itemView.findViewById(R.id.bookNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount() = bookList.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bookName.text = "Name: ${book.bookName}"
        holder.bookGenre.text = "Genre: ${book.bookGenre}"
        holder.bookPublisher.text = "Publisher: ${book.bookPublisher}"
        holder.bookNumber.text = "Number: ${book.bookNumber}"
    }

    fun updateList(newList: List<BookData>) {
        bookList = newList
        notifyDataSetChanged()
    }
}