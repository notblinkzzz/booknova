package com.example.kotlinfinals

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinfinals.databinding.ActivityMainBinding

// this activity serves as the main navigation menu for the app

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // button to add book
        binding.mainAdd.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
            finish()
        }

        // button to update book
        binding.mainUpdate.setOnClickListener {
            startActivity(Intent(this, UpdateActivity::class.java))
            finish()
        }

        // button to delete book
        binding.mainDelete.setOnClickListener {
            startActivity(Intent(this, DeleteActivity::class.java))
            finish()
        }

        // button to view books
        binding.mainView.setOnClickListener {
            startActivity(Intent(this, ViewBooksActivity::class.java))
        }
    }
}
