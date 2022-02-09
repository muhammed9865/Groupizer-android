package com.example.groupizer.ui

import android.app.Activity
import android.content.Context
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.groupizer.Constants
import com.example.groupizer.R
import com.example.groupizer.ui.dashboard.DashboardActivity
import com.google.android.material.snackbar.Snackbar

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun CharSequence?.isValidPassword() = !isNullOrEmpty() && length >= 5
fun CharSequence?.isValidName() = !isNullOrEmpty() && length >= 4

fun Fragment.hideKeyboard(view: View) {
    val imm: InputMethodManager =
        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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

fun Fragment.showError(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        .setBackgroundTint(requireContext().getColor(R.color.red))
        .show()
}

fun DashboardActivity.saveToken(): Boolean {
    intent?.let {
        val token = it.getStringExtra(getString(R.string.current_user))
        val authToken = "${Constants.BEARER} $token"
        getSharedPreferences(getString(R.string.user_token),Context.MODE_PRIVATE)
            .edit()
            .putString(getString(R.string.user_token), authToken)
            .apply()
        return true
    }
    return false

}

fun Activity.getToken(): String? {
    return getSharedPreferences(getString(R.string.user_token),Context.MODE_PRIVATE)
        .getString(getString(R.string.user_token), null)
}

fun Fragment.getToken() = requireActivity().getToken()

fun Fragment.saveId(id: Int) {
    Log.d(TAG, "id saveId: $id")
    requireActivity().getSharedPreferences(getString(R.string.user_id), Context.MODE_PRIVATE)
        .edit()
        .putInt(getString(R.string.user_id), id)
        .apply()
}

fun Fragment.getID(): Int {
    val id = requireActivity().getSharedPreferences(getString(R.string.user_id), Context.MODE_PRIVATE)
        .getInt(getString(R.string.user_id), -1)
    Log.d(TAG, "id saveId: $id")
    return id
}

private const val TAG = "Utilities"