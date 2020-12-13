package com.emptydev.vebra.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordsDatabaseDao {
    @Insert
    suspend fun insert(words: Words)

    @Update
    suspend fun update(words: Words)

    @Query("Delete FROM words_table")
    suspend fun clear()

    @Query("Select * FROM words_table WHERE wordId==:key")
    suspend fun get(key:Long):Words

    @Query("SELECT * FROM words_table ORDER BY wordId DESC")
    fun getAllWordsLists():LiveData<List<Words>>

}