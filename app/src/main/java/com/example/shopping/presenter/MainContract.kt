package com.example.shopping.presenter

import com.example.shopping.BasePresenter
import com.example.shopping.BaseView
import com.example.shopping.data.Purchase

interface MainView : BaseView {
    fun showNewItem(purchase : Purchase)

    fun showArchive(data: List<Purchase>)
}

interface MainPresenter : BasePresenter<MainView> {
    fun addPurchase(image : ByteArray, text : String)

    fun archiveAll()
}