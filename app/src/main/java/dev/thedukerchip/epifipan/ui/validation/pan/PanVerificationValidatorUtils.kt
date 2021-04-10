package dev.thedukerchip.epifipan.ui.validation.pan

import android.annotation.SuppressLint
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val BIRTHDATE_FORMAT = "ddMMyyyy"
private const val PAN_FORMAT = "[A-Z]{5}[0-9]{4}[A-Z]"

@SuppressLint("SimpleDateFormat")
private fun getDateFormat(): SimpleDateFormat {
    return SimpleDateFormat(BIRTHDATE_FORMAT).apply {
        isLenient = false
    }

}

private fun parseDateString(date: String): Calendar? {
    var calendar: Calendar? = null
    val birthDateFormat = getDateFormat()

    try {
        val formattedDate = birthDateFormat.parse(date)
        if (formattedDate != null) {
            calendar = Calendar.getInstance()
            calendar!!.time = formattedDate
        }
    } catch (e: ParseException) {
        calendar = null
    }

    return calendar
}

fun isValidDate(birthday: String): Boolean {
    var isValid = false
    val inputDate: Date? = parseDateString(birthday)?.time

    if (inputDate != null) {
        val formatter = getDateFormat()
        val today: Date = formatter.parse(formatter.format(Date()))!!

        Log.i("DateIssue", "Today: ${today.time} Birthdate: ${inputDate.time}")
        isValid = inputDate.before(today)
    }

    return isValid
}

fun isValidPan(pan: String): Boolean {
    val panFormat = Regex(PAN_FORMAT)
    return pan.matches(panFormat)
}