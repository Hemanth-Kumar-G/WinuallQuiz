package com.hemanth.winuhall.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

object Util {
    fun timeFormatString(millisUntilFinished: Long): String {
        var millisUntilFinished: Long = millisUntilFinished

        val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
        millisUntilFinished -= TimeUnit.DAYS.toMillis(days)

        val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
        millisUntilFinished -= TimeUnit.HOURS.toMillis(hours)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
        millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes)

        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
        return String.format(
            Locale.getDefault(),
            "%01d hr : %02d min : %02d sec", hours, minutes, seconds
        )
    }


    fun convertISOToDateTime(s: String?): String? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = Date.from(Instant.parse(s))
            val formatter = SimpleDateFormat("hh:mm a,dd MMM,yyyy")
            formatter.format(date)
        } else s
}
