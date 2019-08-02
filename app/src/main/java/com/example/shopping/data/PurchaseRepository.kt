package com.example.shopping.data

import javax.inject.Inject

class PurchaseRepository @Inject constructor(
    private val localDataSource: PurchaseLocalDataSource
) : Repository {
    override suspend fun archivePurchase(purchase: Purchase) {
        localDataSource.updatePurchase(purchase.apply {
            isArchive = true
        })
    }

    override suspend fun getAllPurchase(): Result<List<Purchase>> {
        return localDataSource.getAllPurchase()
    }

    override suspend fun archiveAllPurchase() {
        localDataSource.archiveAllPurchase()
    }

    override suspend fun getAllArchivePurchase(): Result<List<Purchase>> {
        return localDataSource.getAllArchivePurchase()
    }

    override suspend fun addPurchase(purchase: Purchase): Result<Unit> {
        return localDataSource.addPurchase(purchase)
    }
}