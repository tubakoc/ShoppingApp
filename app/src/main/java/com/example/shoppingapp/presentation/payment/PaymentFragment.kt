package com.example.shoppingapp.presentation.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.fragment.findNavController
import com.example.shoppingapp.R
import com.example.shoppingapp.common.checkMonthYear
import com.example.shoppingapp.common.isCreditCardNumberValid
import com.example.shoppingapp.common.isNullorEmpty
import com.example.shoppingapp.common.viewBinding
import com.example.shoppingapp.databinding.FragmentPaymentBinding
import com.google.android.material.textfield.TextInputEditText

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    private val monthList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private val yearList = listOf(2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032)

    private var monthValue = 0
    private var yearValue = 0



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val monthAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_menu, monthList)
        val yearAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_menu, yearList)

        with(binding){
            actExpireOnMonth.setAdapter(monthAdapter)
            actExpireOnYear.setAdapter(yearAdapter)

            actExpireOnMonth.setOnItemClickListener { _, _, position, _ ->
                monthValue = monthList[position]
            }

            actExpireOnYear.setOnItemClickListener { _, _, position, _ ->
                yearValue = yearList[position]
            }

            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnPay.setOnClickListener {
                if (checkInfos(
                        etCardholderName,
                        etCreditCardNumber,
                        actExpireOnMonth,
                        actExpireOnYear,
                        etCvcCode,
                        etAddress
                    )
                ) {
                    findNavController().navigate(R.id.paymentToSuccess)
                }


            }

        }
    }

    private fun checkInfos(etCardholderName: TextInputEditText,
                           etCreditCardNumber: TextInputEditText,
                           actExpireOnMonth: AutoCompleteTextView,
                           actExpireOnYear: AutoCompleteTextView,
                           etCvcCode: TextInputEditText,
                           etAddress: TextInputEditText): Boolean {

        val checkInfos = when {
            etCardholderName.isNullorEmpty(getString(R.string.invalid_cardholder_name)).not() -> false
            etCreditCardNumber.isNullorEmpty(getString(R.string.invalid_credit_card_number)).not() -> false
            etCreditCardNumber.isCreditCardNumberValid(getString(R.string.invalid_cart_format)).not() -> false
            actExpireOnMonth.checkMonthYear(monthValue, getString(R.string.invalid_month)).not() -> false
            actExpireOnYear.checkMonthYear(yearValue, getString(R.string.invalid_year)).not() -> false
            etCvcCode.isNullorEmpty(getString(R.string.invalid_cvc_code)).not() -> false
            etAddress.isNullorEmpty(getString(R.string.invalid_address)).not() -> false
            else -> true
        }
        return checkInfos
    }

}