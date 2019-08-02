package com.example.shopping.presenter


import com.example.shopping.data.Purchase
import com.example.shopping.data.PurchaseRepository
import com.example.shopping.data.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(
        private val purchaseRepository: PurchaseRepository
) : MainPresenter {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override var view: MainView? = null

    override fun addPurchase(image: ByteArray, text: String) {
        scope.launch {
            val purchase = Purchase(thumb = image, title = text, id = 0)
            when(purchaseRepository.addPurchase(purchase)) {
                is Result.Success -> view?.showNewItem(purchase)
            }

        }
    }

    override fun archiveAll() {
        scope.launch {
            purchaseRepository.archiveAllPurchase()
            val result = purchaseRepository.getAllArchivePurchase()
            when(result) {
                is Result.Success -> view?.showArchive(result.data)
            }
        }
    }
}