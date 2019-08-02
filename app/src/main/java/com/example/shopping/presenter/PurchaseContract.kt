package com.example.shopping.presenter

import com.example.shopping.BasePresenter
import com.example.shopping.BaseView
import com.example.shopping.data.Purchase
import com.example.shopping.data.PurchaseType

interface PurchaseView : BaseView {
    fun showPurchase(list : List<Purchase>)
}

interface PurchasePresenter : BasePresenter<PurchaseView> {
    fun load(type : PurchaseType?)
}