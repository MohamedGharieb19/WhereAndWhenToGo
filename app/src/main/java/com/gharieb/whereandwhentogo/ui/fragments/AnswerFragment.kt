package com.gharieb.whereandwhentogo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gharieb.whereandwhentogo.R
import com.gharieb.whereandwhentogo.databinding.FragmentAnswerBinding
import com.gharieb.whereandwhentogo.viewModels.AnswerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerFragment : Fragment() {
    private lateinit var binding: FragmentAnswerBinding
    private lateinit var answerViewModel: AnswerViewModel
    private lateinit var countryName: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAnswerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        answerViewModel = ViewModelProvider(this)[AnswerViewModel::class.java]
        getCountryName()
        addTitle()
        sendQuestion()
        addAnswerOfQuestion()
        showProgressBar()

        binding.backArrowButton.setOnClickListener {
            findNavController().navigate(R.id.questionFragment)
        }
    }

    private fun addAnswerOfQuestion(){
        answerViewModel.messageList.observe(viewLifecycleOwner){
            binding.answerQuestionView.text = it.message
            hideProgressBar()
        }
    }

    private fun sendQuestion(){
        val question = "When is the best time to go to $countryName? " +
                "and where is the best places in it ? and a brief about it as a points"
        answerViewModel.callApi(question)
    }

    private fun getCountryName(){
        val data = arguments
        if (data != null){
            countryName = data.getString("countryName").toString()
        }
    }

    private fun addTitle(){
        val title = "Where / When is the best time to go to $countryName?"
        binding.questionTextView.text = title
    }

    private fun hideProgressBar() {
        binding.ProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.ProgressBar.visibility = View.VISIBLE
    }

}