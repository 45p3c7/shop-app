package com.example.shopping.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class PurchaseLocalDataSource @Inject constructor(
    private val db : AppDatabase
) : DataSource {

    override suspend fun getPurchase(id: Int): Result<Purchase> = withContext(Dispatchers.IO) {
        try {
            Result.Success(db.purchaseDao().getPurchase(id))
        } catch (e : Exception) {
            Result.Error(e)
        }
    }

    override suspend fun addPurchase(purchase: Purchase): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            Result.Success(db.purchaseDao().addPurchase(purchase))
        } catch (e : Exception) {
            Result.Error(e)
        }
    }

    override suspend fun updatePurchase(purchase: Purchase) = withContext(Dispatchers.IO) {
        db.purchaseDao().updatePurchase(purchase)
    }

    override suspend fun deletePurchase(purchase: Purchase) = withContext(Dispatchers.IO) {
        db.purchaseDao().deletePurchase(purchase)
    }

    override suspend fun getAllPurchase(): Result<List<Purchase>> = withContext(Dispatchers.IO) {
        try {
            Result.Success(db.purchaseDao().getAll())
        } catch (e : Exception) {
            Result.Error(e)
        }
    }

    override suspend fun archiveAllPurchase() = withContext(Dispatchers.IO) {
        db.purchaseDao().updateAllPurchase()
    }

    override suspend fun getAllArchivePurchase(): Result<List<Purchase>> = withContext(Dispatchers.IO) {
        try {
            Result.Success(db.purchaseDao().getAllArchive())
        } catch (e : Exception) {
            Result.Error(e)
        }
    }
}