package com.example.kotlinfinals

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinfinals.databinding.ActivityViewBooksBinding
import com.google.firebase.database.*

// this activity displays all books and handles search functionality

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

        // initialize recyclerview
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        bookAdapter = BookAdapter(bookList)
        binding.recyclerView.adapter = bookAdapter

        // back to main
        binding.backButton.setOnClickListener {
            finish()
        }

        // search for a book using its number
        binding.searchButton.setOnClickListener {
            val bookNumber = binding.searchEditText.text.toString()
            if (bookNumber.isNotEmpty()) {
                searchBook(bookNumber)
            } else {
                Toast.makeText(this, "enter a book number", Toast.LENGTH_SHORT).show()
            }
        }

        // view all books in the database
        binding.viewAllButton.setOnClickListener {
            loadBooks()
        }
    }

    // function to load all books from firebase
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
                Toast.makeText(this@ViewBooksActivity, "failed to load", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // function to search a book by its number
    private fun searchBook(bookNumber: String) {
        databaseReference.child(bookNumber).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val book = snapshot.getValue(BookData::class.java)
                book?.let { bookAdapter.updateList(listOf(it)) }
            } else {
                Toast.makeText(this, "book not found", Toast.LENGTH_SHORT).show()
                bookAdapter.updateList(emptyList())
            }
        }.addOnFailureListener {
            Toast.makeText(this, "error occurred", Toast.LENGTH_SHORT).show()
        }
    }
}
