package com.emptydev.vebra.wordslist

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _navigateToEditWords = MutableLiveData<Words>()
    val navigateToEditWords:LiveData<Words> get()=_navigateToEditWords
    val createWordListNavigate=MutableLiveData<Boolean>()
    fun onAdd(){
        Log.d("D_WordsListViewModel", "onAdd: ")
        viewModelScope.launch {

            val map= mapOf<String,String>(Pair("word1","translate1"),Pair("word2","translate2"))
            val word_list = Words(lastResultPrc = 90, words = mapToString(map), name = "Test", numWords = 1)
            insert(word_list)
            _navigateToEditWords.value=words.value!!.first()

        }

    }
    private suspend fun insert(night: Words) {

        database.insert(night)
    }
}