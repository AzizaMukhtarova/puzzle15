package com.example.puzzle.Views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.puzzle.R

class First_Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_menu)
        findViewById<View>(R.id.buttonPlay).setOnClickListener { v: View? ->
            val intent = Intent(this, Select_Type_Game::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.buttonAbout).setOnClickListener { v: View? ->
            val intent = Intent(this, About_Activity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.buttonExit).setOnClickListener { v: View? -> finish() }
    }
}