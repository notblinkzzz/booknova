package com.example.kotlinfinals

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinfinals.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// this activity handles updating book data in firebase

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // navigate back to main
        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // when update button is clicked
        binding.updateButton.setOnClickListener {
            val bookNumber = binding.referenceBookNumber.text.toString()
            val bookName = binding.updateBookName.text.toString()
            val bookGenre = binding.updateBookGenre.text.toString()
            val bookPublisher = binding.updateBookPublisher.text.toString()

            updateData(bookNumber, bookName, bookGenre, bookPublisher)
        }
    }

    // function to update data in firebase
    private fun updateData(bookNumber: String, bookName: String, bookGenre: String, bookPublisher: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Book Information")

        val bookData = mutableMapOf<String, Any>()
        if (bookName.isNotBlank()) bookData["bookName"] = bookName
        if (bookGenre.isNotBlank()) bookData["bookGenre"] = bookGenre
        if (bookPublisher.isNotBlank()) bookData["bookPublisher"] = bookPublisher

        if (bookData.isEmpty()) {
            Toast.makeText(this, "please enter at least one field to update", Toast.LENGTH_SHORT).show()
            return
        }

        databaseReference.child(bookNumber).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                databaseReference.child(bookNumber).updateChildren(bookData).addOnSuccessListener {
                    binding.referenceBookNumber.text.clear()
                    binding.updateBookName.text.clear()
                    binding.updateBookGenre.text.clear()
                    binding.updateBookPublisher.text.clear()
                    Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "failed to update", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "book number does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "error checking data", Toast.LENGTH_SHORT).show()
        }
    }
}
