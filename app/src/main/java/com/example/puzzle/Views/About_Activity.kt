package com.example.puzzle.Views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.puzzle.R

class About_Activity {
    class About_Activity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_about)
            val bundle = intent.extras
            findViewById<View>(R.id.about_back).setOnClickListener { v: View? -> finish() }
        }
    }
}