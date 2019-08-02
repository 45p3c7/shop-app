package com.example.shopping.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "purchase")
data class Purchase(

        @PrimaryKey(autoGenerate = true)
        val id : Int,
        var title : String = "",
        @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
        var thumb : ByteArray,
        var isArchive : Boolean = false
)