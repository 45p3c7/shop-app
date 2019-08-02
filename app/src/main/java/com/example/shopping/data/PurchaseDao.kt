package com.example.shopping.data

import androidx.room.*


@Dao
interface PurchaseDao {

    @Query("Select * from purchase where isArchive = 0")
    fun getAll() : List<Purchase>

    @Query("Select * from purchase where id = :id")
    fun getPurchase(id : Int) : Purchase

    @Query("Select * from purchase where isArchive = 1")
    fun getAllArchive() : List<Purchase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPurchase(purchase : Purchase)

    @Update
    fun updatePurchase(purchase: Purchase)

    @Query("update purchase set isArchive = 1")
    fun updateAllPurchase()

    @Delete
    fun deletePurchase(purchase: Purchase)
}