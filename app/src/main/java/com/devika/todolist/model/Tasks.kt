package com.devika.todolist.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "tasks")
@Parcelize
data class Tasks(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id:String= UUID.randomUUID().toString(),
    @ColumnInfo(name = "title")
    var title:String=" ",
    @ColumnInfo(name = "description")
    var description:String="",
    @ColumnInfo(name = "completed")
    var isCompleted:Boolean=false

) : Parcelable{
    val isEmpty
    get()=title.isEmpty()||description.isEmpty()

    val isActive
    get() = !isCompleted
}