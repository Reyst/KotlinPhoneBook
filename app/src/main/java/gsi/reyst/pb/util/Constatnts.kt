package gsi.reyst.pb.util

import android.util.Log

val LOGGER_TAG : String = "_LOG"

fun LoggerD(text : String) = Log.d(LOGGER_TAG, text)

fun LoggerE(text : String) = Log.e(LOGGER_TAG, text)