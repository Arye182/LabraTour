package com.example.labratour.presentation.ui.general

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.labratour.R
import com.example.labratour.presentation.ui.home.HomeActivity
import com.example.labratour.presentation.viewmodel.CurrencyViewModel
import kotlinx.android.synthetic.main.fragment_currency.*
import kotlinx.coroutines.flow.collect

class CurrencyFragment : Fragment(R.layout.fragment_currency) {

    private lateinit var currencyViewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currencyViewModel = (activity as HomeActivity?)?.currencyViewModel!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnConvert.setOnClickListener { convert() }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            currencyViewModel.conversion.collect {
                event ->
                when (event) {
                    is CurrencyViewModel.CurrencyEvent.Success -> {
                        progressBar.isVisible = false
                        tvResult.setTextColor(Color.BLACK)
                        tvResult.text = event.resultText
                        Log.i("Places", "CurrencyFragment " + event.resultText)
                    }
                    is CurrencyViewModel.CurrencyEvent.Failure -> {
                        progressBar.isVisible = false
                        tvResult.setTextColor(Color.RED)
                        tvResult.text = event.errorText
                        Log.i("Places", "CurrencyFragment " + event.errorText)
                    }
                    is CurrencyViewModel.CurrencyEvent.Loading -> {
                        progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }

    fun convert() {
        this.currencyViewModel.convert(
            etFrom.text.toString(),
            spFromCurrency.selectedItem.toString(),
            spToCurrency.selectedItem.toString()
        )
    }
}
