package com.example.shopping.di

import com.example.shopping.PurchaseListFragment
import com.example.shopping.presenter.PurchasePresenter
import com.example.shopping.presenter.PurchasePresenterImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PurchaseModule {

    @ContributesAndroidInjector
    abstract fun purchaseFragment() : PurchaseListFragment

    @Binds
    abstract fun purchasePresenter(presenter : PurchasePresenterImpl) : PurchasePresenter
}