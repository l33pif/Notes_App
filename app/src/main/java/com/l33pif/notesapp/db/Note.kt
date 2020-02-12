package com.l33pif.notesapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class Note (

    @ColumnInfo(name = "Title")
    val title: String,

    @ColumnInfo(name = "Note")
    val note: String,

    @ColumnInfo(name = "Date")
    val date: String

):Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}