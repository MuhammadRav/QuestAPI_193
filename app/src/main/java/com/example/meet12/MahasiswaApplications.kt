package com.example.meet12

import android.app.Application
import com.example.meet12.container.AppContainer
import com.example.meet12.container.MahasiswaContainer

class MahasiswaApplications:Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}