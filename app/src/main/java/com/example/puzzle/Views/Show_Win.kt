package com.example.puzzle.Views

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.puzzle.R

class Show_Win : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_win)
        val bundle = intent.extras
        val textScore = findViewById<TextView>(R.id.textScore)
        val textTime = findViewById<TextView>(R.id.textTime)
        findViewById<View>(R.id.buttonBack).setOnClickListener { v: View? ->
            setResult(1)
            finish()
        }
        findViewById<View>(R.id.buttonClose).setOnClickListener { v: View? -> finishAffinity() }
        if (bundle != null) {
            textScore.text = bundle.getString("SCORE", textScore.text as String).toString()
            textTime.text = bundle.getString("TIME", "--:--")
        }
    }
}