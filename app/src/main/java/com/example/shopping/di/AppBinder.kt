package com.example.shopping.di

import com.example.shopping.MainActivity
import com.example.shopping.presenter.MainPresenter
import com.example.shopping.presenter.MainPresenterImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class AppBinder {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @Binds
    abstract fun mainPresenter(mainPresenterImpl: MainPresenterImpl) : MainPresenter
}