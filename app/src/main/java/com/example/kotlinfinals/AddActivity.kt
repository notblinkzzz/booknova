package com.example.kotlinfinals

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinfinals.databinding.ActivityAddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// this activity handles adding book data to firebase

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // navigate back to main activity
        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // save data to firebase
        binding.saveButton.setOnClickListener {
            val bookName = binding.uploadBookName.text.toString()
            val bookGenre = binding.uploadBookGenre.text.toString()
            val bookPublisher = binding.uploadBookPublisher.text.toString()
            val bookNumber = binding.uploadBookNumber.text.toString()

            databaseReference = FirebaseDatabase.getInstance().getReference("Book Information")
            val bookData = BookData(bookName, bookGenre, bookPublisher, bookNumber)

            databaseReference.child(bookNumber).setValue(bookData).addOnSuccessListener {
                binding.uploadBookName.text.clear()
                binding.uploadBookGenre.text.clear()
                binding.uploadBookPublisher.text.clear()
                binding.uploadBookNumber.text.clear()
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}