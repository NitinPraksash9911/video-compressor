package com.example.trellassignment.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.trellassignment.R
import com.google.android.material.snackbar.Snackbar

fun String.snackMessage(color: Int, view: View) {
    val snackBar = Snackbar.make(view, this, Snackbar.LENGTH_LONG)
    snackBar.view.setBackgroundColor(color)
    snackBar.setTextColor(Color.WHITE)
    snackBar.show()
}

private lateinit var alertDialog: AlertDialog

fun Activity.showLoader() {

    val builder = AlertDialog.Builder(this)

    val layoutInflater = this.layoutInflater

    builder.setView(layoutInflater.inflate(R.layout.custom_dialog, null))

    builder.setCancelable(false)

    alertDialog = builder.create();
    alertDialog.show()

}

fun dismissLoader() {
    alertDialog.dismiss()
}

fun View.hideKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}