package com.emptydev.vebra.wordslist

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emptydev.vebra.database.Words
import com.emptydev.vebra.database.WordsDatabaseDao
import com.emptydev.vebra.mapToString
import kotlinx.coroutines.launch


class WordsListViewModel(private val database: WordsDatabaseDao,
                         private val context: Context
) : ViewModel() {
    val words = database.getAllWordsLists()

    fun onAdd(){
        Log.d("D_WordsListViewModel", "onAdd: ")
        viewModelScope.launch {

            val map= mapOf<String,String>(Pair("word1","translate1"),Pair("word2","translate2"))
            val words = Words(lastResultPrc = 90, words = mapToString(map), name = "Test", numWords = 1)
            insert(words)
        }
    }
    private suspend fun insert(night: Words) {

        database.insert(night)
    }
}