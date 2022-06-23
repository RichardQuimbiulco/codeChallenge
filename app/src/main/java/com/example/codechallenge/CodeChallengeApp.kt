package com.example.codechallenge

import android.app.Application
import com.example.codechallenge.di.CodeChallengeComponent
import com.example.codechallenge.di.DaggerCodeChallengeComponent

class CodeChallengeApp: Application() {
    lateinit var component: CodeChallengeComponent

    override fun onCreate() {
        super.onCreate()
        component = initAppComponent()
    }

    private fun initAppComponent() = DaggerCodeChallengeComponent.factory().create(this)
}