package com.example.remeberme.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_data")
data class Contact (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "contactName")
    val contactName: String,
    @ColumnInfo(name = "contacType")
    val contactType: String
)