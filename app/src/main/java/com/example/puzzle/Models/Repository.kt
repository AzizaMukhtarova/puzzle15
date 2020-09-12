package com.example.puzzle.Models

import com.example.puzzle.contracts.Contract
import java.util.*

class Repository (override val pref: LocalStorage): Contract.Model {
    private val list = ArrayList<Int?>()
    override val numbers: List<Int?>
        get() {
            Collections.shuffle(list)
            return list
        }

    init {
//        for (i in 1..15) {
//            list.add(i)
//        }
    }
}