package com.saddict.reinvent.utils

import android.content.Context
import android.widget.Toast

fun Context.toastUtil(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()