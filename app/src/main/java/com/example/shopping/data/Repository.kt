package com.example.shopping.data

interface Repository {

    suspend fun archivePurchase(purchase: Purchase)

    suspend fun getAllPurchase() : Result<List<Purchase>>

    suspend fun getAllArchivePurchase() : Result<List<Purchase>>

    suspend fun archiveAllPurchase()

    suspend fun addPurchase(purchase: Purchase): Result<Unit>
}