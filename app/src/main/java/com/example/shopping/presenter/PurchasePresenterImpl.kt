package com.example.shopping.presenter

import com.example.shopping.data.PurchaseRepository
import com.example.shopping.data.PurchaseType
import com.example.shopping.data.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class PurchasePresenterImpl @Inject constructor(
        private val purchaseRepository: PurchaseRepository
) : PurchasePresenter {

    override var view: PurchaseView? = null

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun load(type: PurchaseType?) {
        scope.launch {
            when (type) {
                PurchaseType.NEW -> {
                    val result = purchaseRepository.getAllPurchase()
                    if (result is Result.Success) view?.showPurchase(result.data)
                }
                PurchaseType.ARCHIVE -> {
                    val result = purchaseRepository.getAllArchivePurchase()
                    if (result is Result.Success) view?.showPurchase(result.data)
                }
            }
        }
    }
}