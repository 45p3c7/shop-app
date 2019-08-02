package com.example.shopping.di

import android.content.Context
import com.example.shopping.ShoppingApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    AppBinder::class,
    PurchaseModule::class
]
)
interface AppComponent : AndroidInjector<ShoppingApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent

    }
}