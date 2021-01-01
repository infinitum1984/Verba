package com.emptydev.vebra.wordslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.emptydev.vebra.R
import com.emptydev.vebra.database.WordsDatabase
import com.emptydev.vebra.databinding.WordsListFragmentBinding
import com.emptydev.vebra.stringToMap

class WordsListFragment : Fragment() {

    private lateinit var viewModel: WordsListViewModel
    private lateinit var binding: WordsListFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.words_list_fragment,container,false)

        val dataSource=WordsDatabase.getInstance(this.requireContext()).wordsDatabaseDao
        val viewModelFactory=WordsListViewModelFactory(dataSource,this.context!!)
        val viewModel= ViewModelProvider(this, viewModelFactory).get(WordsListViewModel::class.java)
        binding.viewModel=viewModel
        binding.lifecycleOwner=this
        val adapter=WordsListAdapter()
        binding.wordsList.adapter=adapter
        viewModel.words.observe(viewLifecycleOwner, Observer {

            adapter.setData(it)

        })
        viewModel.navigateToEditWords.observe(viewLifecycleOwner, Observer {

           this.findNavController()
                   .navigate(WordsListFragmentDirections.actionWordsListFragmentToEditWordsFragment(it.wordId))
        })
        viewModel.createWordListNavigate.observe(viewLifecycleOwner, Observer {
            if (it){
                this.findNavController()
                        .navigate(WordsListFragmentDirections.actionWordsListFragmentToEditWordsFragment(-1))
                viewModel.createWordListNavigate.value=false
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WordsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}