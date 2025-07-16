package com.example.kotlinfinals

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinfinals.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainAdd.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
            finish()
        }

        binding.mainUpdate.setOnClickListener {
            startActivity(Intent(this, UpdateActivity::class.java))
            finish()
        }

        binding.mainDelete.setOnClickListener {
            startActivity(Intent(this, DeleteActivity::class.java))
            finish()
        }

        binding.mainView.setOnClickListener {
            startActivity(Intent(this, ViewBooksActivity::class.java))
        }
    }
}