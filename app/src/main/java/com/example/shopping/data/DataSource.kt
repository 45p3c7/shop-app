package com.example.shopping.data

interface DataSource {

    suspend fun getPurchase(id : Int) : Result<Purchase>

    suspend fun addPurchase(purchase: Purchase): Result<Unit>

    suspend fun updatePurchase(purchase: Purchase)

    suspend fun deletePurchase(purchase: Purchase)

    suspend fun getAllPurchase() : Result<List<Purchase>>

    suspend fun getAllArchivePurchase() : Result<List<Purchase>>

    suspend fun archiveAllPurchase()
}