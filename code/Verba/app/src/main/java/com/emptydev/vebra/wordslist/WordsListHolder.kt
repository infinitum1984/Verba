package com.emptydev.vebra.wordslist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emptydev.vebra.R
import com.emptydev.vebra.database.Words

class WordsListHolder(view : View):RecyclerView.ViewHolder(view) {
    val image:ImageView=view.findViewById(R.id.words_image)
    val numWords:TextView=view.findViewById(R.id.num_words)
    val nameWords:TextView=view.findViewById(R.id.words_name)

    fun bind(item: Words){
        numWords.text="${item.numWords}"
        nameWords.text="${item.name}"
    }
}