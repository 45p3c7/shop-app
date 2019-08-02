package com.example.shopping

import com.example.shopping.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class ShoppingApplication : dagger.android.support.DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out dagger.android.support.DaggerApplication> {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}

