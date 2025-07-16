package com.example.kotlinfinals

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinfinals.databinding.ActivityViewBooksBinding
import com.google.firebase.database.*

class ViewBooksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewBooksBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var bookAdapter: BookAdapter
    private var bookList = mutableListOf<BookData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("Book Information")

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        bookAdapter = BookAdapter(bookList)
        binding.recyclerView.adapter = bookAdapter


        binding.backButton.setOnClickListener {
            finish()
        }

        binding.searchButton.setOnClickListener {
            val bookNumber = binding.searchEditText.text.toString()
            if (bookNumber.isNotEmpty()) {
                searchBook(bookNumber)
            } else {
                Toast.makeText(this, "Enter a book number", Toast.LENGTH_SHORT).show()
            }
        }

        binding.viewAllButton.setOnClickListener {
            loadBooks()
        }
    }

    private fun loadBooks() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookList.clear()
                for (bookSnapshot in snapshot.children) {
                    val book = bookSnapshot.getValue(BookData::class.java)
                    book?.let { bookList.add(it) }
                }
                bookAdapter.updateList(bookList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ViewBooksActivity, "Failed to load", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun searchBook(bookNumber: String) {
        databaseReference.child(bookNumber).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val book = snapshot.getValue(BookData::class.java)
                book?.let { bookAdapter.updateList(listOf(it)) }
            } else {
                Toast.makeText(this, "Book not found", Toast.LENGTH_SHORT).show()
                bookAdapter.updateList(emptyList())
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show()
        }
    }
}