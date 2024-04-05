package org.izolentiy.todolist

import android.app.Application
import org.izolentiy.todolist.di.DaggerAppComponent

class App : Application() {
    val appComponent = DaggerAppComponent.factory().create(context = this)
}