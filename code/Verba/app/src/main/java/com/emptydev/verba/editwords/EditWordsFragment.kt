package com.emptydev.verba.editwords

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.emptydev.verba.*
import com.emptydev.verba.database.WordsDatabase
import com.emptydev.verba.databinding.EditWordsFragmentBinding
import com.emptydev.verba.delete.DeleteDialog
import com.emptydev.verba.mistakes.MistakesDialog
import com.emptydev.verba.training.TrainingType
import com.google.android.material.snackbar.Snackbar

class EditWordsFragment : Fragment() {


    private lateinit var viewModel: EditWordsViewModel

    private lateinit var binding: EditWordsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding= DataBindingUtil.inflate(inflater,R.layout.edit_words_fragment,container,false)
        val arguments = EditWordsFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        Log.d("D_EditWordsFragment","onCreateView: ${arguments.wordsKey}");
        val dataSource= WordsDatabase.getInstance(application).wordsDatabaseDao
        val viewModelFactory=EditWordsViewModelFactory(arguments.wordsKey,dataSource)
        viewModel = ViewModelProvider(this,viewModelFactory).get(EditWordsViewModel::class.java)
        binding.viewModel=viewModel

        binding.lifecycleOwner=this
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete->DeleteDialog(requireContext(),{
                viewModel.deleteSet()
            }).show()
        }
        return true
    }
    fun setupToolBar(){
        //binding.edWordsToolbar.title=viewModel.wordsSet.value!!.name
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.wordsSet.observe(viewLifecycleOwner, Observer {
            binding.textView.text=it?.name
            binding.textView.visibility=View.VISIBLE
            binding.setNameLayout.visibility=View.INVISIBLE
            binding.imageButton.setImageDrawable(requireContext().getDrawable(R.drawable.ic_edit))

            val setStrings= arrayToPairStrings(stringToPairArray(it.words))
            binding.primaryText.setText(setStrings.first)
            binding.translatedText.setText(setStrings.second)
            binding.fabPlay.setImageDrawable(requireContext().getDrawable(R.drawable.ic_play))
            setupToolBar()

        })
        viewModel.onEditSetName.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                binding.textView.visibility = View.GONE
                binding.setNameLayout.visibility = View.VISIBLE
                binding.edWordsName.setText(it!!)
                binding.imageButton.setImageDrawable(requireContext().getDrawable(R.drawable.ic_check))
            }
        })
        viewModel.onSaveSetName.observe(viewLifecycleOwner, Observer {
            if (it==true){
                viewModel.saveSetName(binding.edWordsName.text.toString())
            }
        })
        viewModel.onSaveSet.observe(viewLifecycleOwner, Observer {
            val primaryText=binding.primaryText.text.toString().trim()
            val translatedText=binding.translatedText.text.toString().trim()
            if (primaryText.isEmpty()){
                showExceptionInputEmpty(binding.fabPlay)
                return@Observer
            }
            if (translatedText.isEmpty()){
                showExceptionInputEmpty(binding.fabPlay)
                return@Observer
            }
            val arrPrimary= stringToArray(primaryText)
            val arrTranslate= stringToArray(translatedText)
            if (arrPrimary.size!=arrTranslate.size){
                showExceptionInputNoneEqual(binding.fabPlay)
                return@Observer
            }

            val map = ArrayList<Pair<String,String>>()
            for(i in (0..arrPrimary.size-1) ){
                map.add(Pair(arrPrimary[i],arrTranslate[i]))
            }
            viewModel.saveSet(map)
        })
        viewModel.onPlaySet.observe(viewLifecycleOwner, Observer {
            if (it==true) {

                findNavController().navigate(EditWordsFragmentDirections.actionEditWordsFragmentToTrainingFragment(viewModel.wordsSet.value!!.wordId, processType()))
                viewModel.onSetPlayed()
            }
        })
        viewModel.onShowLastMistakes.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                MistakesDialog(it).show(requireFragmentManager(),"Mistakes")
            }
        })
        viewModel.onSetDeleted.observe(viewLifecycleOwner, Observer {
            if (it==true){
                findNavController().navigate(EditWordsFragmentDirections.actionEditWordsFragmentToWordsListFragment())
            }
        })
        binding.translatedText.setOnClickListener {
            binding.fabPlay.setImageDrawable(requireContext().getDrawable(R.drawable.ic_save))
            viewModel.onTextChanged()
        }

        binding.primaryText.setOnClickListener {
            binding.fabPlay.setImageDrawable(requireContext().getDrawable(R.drawable.ic_save))
            viewModel.onTextChanged()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_words, menu)
    }
    private fun showExceptionInputEmpty(view:View){
        Snackbar.make(view,"Input field is empty!",Snackbar.LENGTH_LONG).show()

    }
    private fun showExceptionInputNoneEqual(view: View){
        Snackbar.make(view,"Different number of words in fields!",Snackbar.LENGTH_LONG).show()
    }
    private fun processType():TrainingType{
        val revers=binding.switchRevers.isChecked
        val last_mistakes=binding.swLastMistake.isChecked

        if (!revers && !last_mistakes) return TrainingType.BASIC

        if (revers && !last_mistakes) return TrainingType.REVERS

        if (!revers && last_mistakes) return TrainingType.LAST_MISTAKE

        if (revers && last_mistakes) return TrainingType.REVERS_LAST_MISTAKE

        return TrainingType.BASIC
    }



}
