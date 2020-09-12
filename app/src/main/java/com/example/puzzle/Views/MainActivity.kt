package com.example.puzzle.Views

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.puzzle.Models.Coordinate
import com.example.puzzle.Models.LocalStorage
import com.example.puzzle.Models.Repository
import com.example.puzzle.Presenters.Presenter
import com.example.puzzle.R
import com.example.puzzle.contracts.Contract
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Contract.View {
    private lateinit var presenter: Contract.Presenter
    private lateinit var buttons: Array<Array<Button?>>
    private var textScore: TextView? = null
    private var strings: Array<String?>?=null
    private var textTimer: Chronometer? = null
    private val preferences by lazy { LocalStorage(this)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bundle = intent.extras
        title = "Puzzle 15"
        loadViews()
        presenter = Presenter(this, Repository(preferences))
        presenter.startGame()
    }

    private fun loadViews() {
        findViewById<View>(R.id.buttonFinish).setOnClickListener { v: View? -> presenter!!.finish() }
        findViewById<View>(R.id.buttonRestart).setOnClickListener { v: View? -> presenter!!.restart() }
        textTimer = findViewById(R.id.textTimer)
        textScore = findViewById(R.id.textScore)
        val group = findViewById<ViewGroup>(R.id.group)
        buttons = Array(4) { arrayOfNulls<Button>(4) }
        strings = arrayOfNulls(16)
        for (i in 0 until group.childCount) {
            val button = group.getChildAt(i) as Button
            buttons[i / 4][i % 4] = button
            button.setOnClickListener { v: View -> presenter!!.click(v.tag as Coordinate) }
            button.tag = Coordinate(i / 4, i % 4)
        }
    }

    private fun click(view: View) {
        val button = view as Button
        val c = button.tag as Coordinate
        presenter!!.click(c)
    }

    override fun finishGame() {
        finish()
    }

    override fun loadData(data: List<String>) {
        for (i in data!!.indices) {
            buttons[i / 4][i % 4]!!.text = data[i].toString()
        }    }



    override fun setElementText(coordinate: Coordinate?, s: String?) {
        buttons[coordinate!!.x][coordinate.y]!!.text = s
    }

    override fun getElementText(coordinate: Coordinate?): String {
        return buttons[coordinate!!.x][coordinate.y]!!.text.toString()
    }

    override fun showConfirmDialog() {
        confirm.visibility = View.VISIBLE    }

    override fun hideConfirmDialog() {
        confirm.visibility = View.INVISIBLE    }

    override fun setScore(score: Int) {
        textScore!!.text = score.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1) {
            presenter!!.finish()
        }
        if (resultCode == 2) {
            presenter = Presenter(this,Repository(preferences))
            presenter.restart()
        }
    }

    override fun showWin() { //        Toast.makeText(this, "You win", Toast.LENGTH_LONG).show();
        val intent = Intent(this, Show_Win::class.java)
        intent.putExtra("SCORE", "Score: " + textScore!!.text)
        intent.putExtra("TIME", "Time: " + textTimer!!.text)
        startActivityForResult(intent, 2)
    }

    override fun startTimer(base: Long) {
        textTimer!!.stop()
        textTimer!!.base = SystemClock.elapsedRealtime()
        textTimer!!.start()    }

    override fun getBaseTime(): Long {
        return SystemClock.elapsedRealtime()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        for (i in 0..15) {
            strings!![i] = buttons[i / 4][i % 4]!!.text.toString()
        }
        outState.putStringArray("BUTTONS", strings)
        outState.putLong("TIME", textTimer!!.base)
        outState.putString("SCORE", textScore!!.text as String)
        outState.putInt("SPACE_X", presenter!!.space!!.x)
        outState.putInt("SPACE_Y", presenter!!.space!!.y)
        outState.putInt("STEP", presenter!!.step)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            textTimer!!.base = (savedInstanceState["TIME"] as Long?)!!
            textTimer!!.start()
            textScore!!.text = savedInstanceState["SCORE"] as String?
            strings = savedInstanceState["BUTTONS"] as Array<String?>?
            for (i in 0..15) {
                buttons[i / 4][i % 4]!!.text = strings!![i]
                Log.d("LOL", strings!![i])
            }
            val x = savedInstanceState["SPACE_X"] as Int
            val y = savedInstanceState["SPACE_Y"] as Int
            val step = savedInstanceState["STEP"] as Int
            presenter!!.space = Coordinate(x, y)
            presenter!!.step = step
        }
    }
}
