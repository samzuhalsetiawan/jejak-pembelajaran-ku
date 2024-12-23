package com.wahyusembiring.roomksp2.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
   tableName = "homework"
)
data class Homework(
   @PrimaryKey(autoGenerate = true)
   val id: Int = 0,

   val title: String,

   @ColumnInfo(name = "due_date")
   val dueDate: Date,

   val reminder: Time,

//   @ColumnInfo(name = "due_date")
//   val dueDate: Long,
//
//   val reminder: Long,

   val subject: Int,

   val description: String,
)