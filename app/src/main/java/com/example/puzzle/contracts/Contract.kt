package com.example.puzzle.contracts

import com.example.puzzle.Models.Coordinate
import com.example.puzzle.Models.LocalStorage

interface Contract {
    interface Model {
        val numbers: List<Int?>?
        val pref: LocalStorage
    }

    interface View {
        fun finishGame()
        fun loadData(data: List<String>)
        fun setElementText(coordinate: Coordinate?, s: String?)
        fun getElementText(coordinate: Coordinate?): String
        fun showConfirmDialog()
        fun hideConfirmDialog()
        fun setScore(score: Int)
        fun showWin()
        fun startTimer(base: Long = 0)
        fun getBaseTime(): Long
    }


    interface Presenter {
        fun startGame()
        fun saveData()
        fun finish()
        fun restart()
        fun click(coordinate: Coordinate?)
        var space: Coordinate?
        var step: Int
    }
}