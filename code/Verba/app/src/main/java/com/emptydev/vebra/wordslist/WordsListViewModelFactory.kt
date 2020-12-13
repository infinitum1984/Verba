package com.emptydev.vebra.wordslist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emptydev.vebra.database.WordsDatabaseDao

class WordsListViewModelFactory(
    private val dataSource:WordsDatabaseDao,
    private val context: Context
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordsListViewModel::class.java)){
            return WordsListViewModel(dataSource,context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}