package com.me.baseproject.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.*
import java.util.*
import java.util.regex.Pattern

fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow((currentFocus ?: View(this)).windowToken, 0)
}