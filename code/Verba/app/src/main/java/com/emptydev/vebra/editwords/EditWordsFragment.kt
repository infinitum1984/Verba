package com.emptydev.vebra.editwords

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emptydev.vebra.R

class EditWordsFragment : Fragment() {

    companion object {
        fun newInstance() = EditWordsFragment()
    }

    private lateinit var viewModel: EditWordsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_words_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditWordsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}