package com.gharieb.whereandwhentogo.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gharieb.whereandwhentogo.R
import com.gharieb.whereandwhentogo.adapters.CountrySpinnerAdapter
import com.gharieb.whereandwhentogo.databinding.FragmentQuestionBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class QuestionFragment : Fragment() {

    private lateinit var binding: FragmentQuestionBinding
    private lateinit var selectedCountryFromSpinner: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSpinner()

        binding.submitButton.setOnClickListener {
            if (selectedCountryFromSpinner != " Select a Country ") sendCountryName() else showAlertDialog()
        }

    }

    private fun setUpSpinner() {
        val countrySpinner: Spinner = binding.countrySpinner // Replace with your spinner view

        val locales: Array<String> = Locale.getISOCountries()
        val countries = ArrayList<String>()
        countries.add(" Select a Country ")

        for (countryCode in locales) {
            val obj = Locale("", countryCode)
            countries.add(obj.displayCountry)
        }
        countries.sort()

        val adapter = CountrySpinnerAdapter(countries)
        countrySpinner.adapter = adapter

        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCountry = parent.getItemAtPosition(position) as String
                selectedCountryFromSpinner = selectedCountry

                binding.submitButton.isEnabled = true
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.submitButton.isEnabled = false
            }
        }
    }

    private fun showAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage("Please Select a Country!")
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun sendCountryName(){
        val fragment = AnswerFragment()
        val bundle = Bundle()
        bundle.putString("countryName",selectedCountryFromSpinner)
        fragment.arguments = bundle
        findNavController().navigate(R.id.answerFragment,bundle)
    }
}
