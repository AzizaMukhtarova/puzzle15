package com.example.puzzle.Presenters

import com.example.puzzle.Models.Coordinate
import com.example.puzzle.contracts.Contract

class Presenter(private val view: Contract.View, private val model: Contract.Model) : Contract.Presenter {
    override var space: Coordinate? = Coordinate(3, 3)
    override var step = 0

    override fun finish() {
        view.finishGame()
    }
    override fun saveData() {
        model.pref.isSaved = true
        model.pref.score = step
        model.pref.coordinateX = space!!.x
        model.pref.coordinateY = space!!.y
        model.pref.endTime = view.getBaseTime()
        model.pref.buttonsPos = getButtonsPos()
    }
    private fun getButtonsPos(): ArrayList<String> {
        val ls = ArrayList<String>()
        for (i in 0..15) {
            ls.add(view.getElementText(Coordinate(i / 4, i % 4)))
        }
        return ls
    }
    override fun startGame() {
        if (model.pref.isSaved) {
            view.showConfirmDialog()
        } else {
            restart()
        }
    }

    override fun restart() {
        view.hideConfirmDialog()
        if (model.pref.isSaved) {
            view.setScore(model.pref.score)
            step = model.pref.score
            val setTime = view.getBaseTime() - (model.pref.endTime - model.pref.beginTime)
            view.startTimer(setTime)
            model.pref.beginTime = view.getBaseTime() - (model.pref.endTime - model.pref.beginTime)
            space = Coordinate(model.pref.coordinateX, model.pref.coordinateY)
            view.loadData(model.pref.buttonsPos)
        } else{
            step = 0
            view.setScore(step)
            space = Coordinate(3, 3)
            view.setElementText(space, "")
            view.loadData(getNumbersToString())
            view.startTimer()
            model.pref.beginTime = view.getBaseTime()
        }
        model.pref.isSaved = false
    }



private fun getNumbersToString(): ArrayList<String> {
    val ls = ArrayList<String>()
    for (i in model.numbers!!) {
        ls.add(i.toString())
    }
    return ls
}

    override fun click(coordinate: Coordinate?) {
        val dx = StrictMath.abs(space!!.x - coordinate!!.x)
        val dy = StrictMath.abs(space!!.y - coordinate.y)
        if (dx + dy == 1) {
            step++
            view.setScore(step)
            val t1 = view.getElementText(coordinate)
            view.setElementText(space, t1)
            view.setElementText(coordinate, "")
            space = coordinate
            if (isWin) {
                view.showWin()
                restart()
            }
        }
    }

    private fun beNotWin(list: List<Int?>?): Boolean {
        var invers = 0
        val n = list!!.size
        for (i in 0 until n - 1) {
            for (j in i + 1 until n) {
                if (list[i]!! > list[j]!!) {
                    invers++
                }
            }
        }
        return if (invers % 2 == 0) {
            true
        } else {
            false
        }
    }

    private val isWin: Boolean
        private get() {
            if (space!!.y != 3 || space!!.x != 3) return false
            for (i in 0..14) {
                val text  = view.getElementText(Coordinate(i / 4, i % 4))
                if (text != (i + 1).toString()) {
                    return false
                }
            }
            return true
        }

    init {
        restart()
    }
}
