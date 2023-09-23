package com.example.shoppingapp.common

import android.graphics.Paint
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context).load(url).into(this)

}

fun String.isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    if (!email.isNullOrEmpty() && email.matches(emailRegex.toRegex())) {
        return true
    }
    return false
}

fun String.isValidPassword(password:String): Boolean{
    if(!password.isNullOrEmpty() && password.length >= 6){
        return true
    }
    return false
}


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.GONE
}

fun TextView.setStrikeThrough() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun TextInputEditText.isNullorEmpty(errorString: String): Boolean {
    val textInputLayout = this.parent.parent as TextInputLayout
    return if (text.toString().trim().isNotEmpty()) {
        textInputLayout.isErrorEnabled = false
        true
    } else {
        textInputLayout.error = errorString
        false
    }
}

fun AutoCompleteTextView.checkMonthYear(value: Int, errorString: String): Boolean {
    val textInputLayout = this.parent.parent as TextInputLayout
    return if (value != 0) {
        textInputLayout.isErrorEnabled = false
        true
    } else {
        textInputLayout.error = errorString
        false
    }
}

fun TextInputEditText.isCreditCardNumberValid(errorMessage: String): Boolean {
    val text = this.text?.toString() ?: ""
    if (text.length != 16 || !text.isNumeric()) {
        this.error = errorMessage
        return false
    }
    return true
}

fun String.isNumeric(): Boolean {
    return this.matches(Regex("[0-9]+"))
}