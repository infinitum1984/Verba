package com.emptydev.vebra.wordslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emptydev.vebra.R
import com.emptydev.vebra.database.Words

class WordsListAdapter:RecyclerView.Adapter<WordsListHolder>() {
    private var dataList= listOf<Words>()
    fun setData(list:List<Words>){
        dataList=list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.word_item,parent,false)
        return WordsListHolder(view)
    }

    override fun onBindViewHolder(holder: WordsListHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount()=dataList.size
}