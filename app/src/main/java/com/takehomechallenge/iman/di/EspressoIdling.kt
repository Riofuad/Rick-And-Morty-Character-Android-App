package com.takehomechallenge.iman.di

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdling {
    private const val RES = "GLOBAL"
    val countingIdlingResource = CountingIdlingResource(RES)

    val idlingResource: IdlingResource
        get() = countingIdlingResource

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.decrement()
    }
}