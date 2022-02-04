package com.example.groupizer.ui

import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import java.util.regex.Matcher

fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun CharSequence?.isValidPassword() = !isNullOrEmpty() && length >= 5
fun CharSequence?.isValidName() = !isNullOrEmpty() && length >= 4

fun Fragment.hideKeyboard(view: View) {
    val imm: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.showProgress(p: ProgressBar, b: Button) {
    p.visibility = View.VISIBLE
    b.visibility = View.INVISIBLE
}

fun Fragment.hideProgress(p: ProgressBar, b: Button) {
    p.visibility = View.INVISIBLE
    b.visibility = View.VISIBLE
}