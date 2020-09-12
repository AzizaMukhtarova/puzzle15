package com.example.puzzle.Views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.puzzle.R

class Select_Type_Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_type_game)
        val bundle = intent.extras
        findViewById<View>(R.id.button_3_to_3).setOnClickListener { v: View? ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.button_4_to_4).setOnClickListener { v: View? ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.button_5_to_5).setOnClickListener { v: View? ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.button_back_from_type).setOnClickListener { v: View? -> finish() }
    }
}