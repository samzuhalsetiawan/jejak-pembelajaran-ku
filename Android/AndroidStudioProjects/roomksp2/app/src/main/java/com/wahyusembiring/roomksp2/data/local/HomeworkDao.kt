package com.wahyusembiring.roomksp2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wahyusembiring.roomksp2.domain.model.Homework
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeworkDao {

   @Insert(
      entity = Homework::class,
      onConflict = OnConflictStrategy.ABORT
   )
   suspend fun insertHomework(homework: Homework): Long

   @Query("SELECT * FROM homework")
   fun getAllHomeworkAsFlow(): Flow<List<Homework>>

}