package com.example.kotlinfinals

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinfinals.databinding.ActivityDeleteBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// this activity handles deleting book data from firebase

class DeleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // navigate back to main activity
        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // when delete button is clicked
        binding.deleteButton.setOnClickListener {
            val bookNumber = binding.deleteBookNumber.text.toString()
            if (bookNumber.isNotEmpty()) {
                deleteData(bookNumber)
            } else {
                Toast.makeText(this, "please enter book number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // function to delete data from firebase
    private fun deleteData(bookNumber: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Book Information")

        databaseReference.child(bookNumber).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                databaseReference.child(bookNumber).removeValue().addOnSuccessListener {
                    binding.deleteBookNumber.text.clear()
                    Toast.makeText(this, "deleted successfully", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "unable to delete", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "book number does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "error checking book number", Toast.LENGTH_SHORT).show()
        }
    }
}
