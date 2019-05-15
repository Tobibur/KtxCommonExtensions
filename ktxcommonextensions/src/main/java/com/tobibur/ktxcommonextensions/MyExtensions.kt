package com.tobibur.ktxcommonextensions

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.*
import androidx.appcompat.app.AlertDialog
import id.zelory.compressor.Compressor
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun String.addJWT(): String {
    return "JWT ".plus(this)
}

fun String.capitalizeAll(): String? {

    val listString = this.split(" ")
    var string: String? = ""
    for (strings in listString) {
        if (string != "") {
            string = string + " " + strings.capitalize()
        } else {
            string = strings.capitalize()
        }
    }
    return string
}

fun Context.vibrateDevice() {
    val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    // Vibrate for 500 milliseconds
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        //deprecated in API 26
        v.vibrate(500)
    }
}

fun AlertDialog.invisibleBg() {
    this.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
}


fun String.toDate(): Date {
    val month = this.substringAfter("-").substringBefore('-')
    val capMonth = month.capitalize()
    var a: String? = null


    when (capMonth) {
        "Jan" -> a = "1"
        "Feb" -> a = "2"
        "Mar" -> a = "3"
        "Apr" -> a = "4"
        "May" -> a = "5"
        "Jun" -> a = "6"
        "Jul" -> a = "7"
        "Aug" -> a = "8"
        "Sep" -> a = "9"
        "Oct" -> a = "10"
        "Nov" -> a = "11"
        "Dec" -> a = "12"
    }
    val convertedDate = this.replace(capMonth, a!!)
    val format = SimpleDateFormat("dd-MM-yyyy")

    return format.parse(convertedDate)
}


fun String.removeJWT(): String {
    return this.removePrefix("JWT ")
}

fun String.addComma(): String {
    return ",".plus(this)
}

fun String.addQuestionNumber(q_no: Int): String {
    return (q_no + 1).toString().plus(") ").plus(this)
}

fun ViewGroup.inflate(layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this.context).inflate(layoutRes, this, attachToRoot)
}

fun makeJSONObj(key: String, value: Any): JSONObject {

    val jsonObject = JSONObject()
    try {
        jsonObject.put(key, value)
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    return jsonObject
}


fun getDateDiff(endDate: String, startDate: String): Int {
    val format = SimpleDateFormat("yyyy-MM-dd")
    val diff = format.parse(endDate).time - format.parse(startDate).time
    val second = diff / 1000
    val minute = second / 60
    val hour = minute / 60
    val days = hour / 24
    return days.toInt()

}

fun Context.loadingDialog(resLayout: Int, cancelable: Boolean = true): Dialog {
    val loading = Dialog(this)
    loading.requestWindowFeature(Window.FEATURE_NO_TITLE)
    loading.setCancelable(cancelable)
    loading.setContentView(resLayout)
    return loading
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Window.removeStatusBar() {
    this.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

fun Window.changeStatusBarColor(color: Int) {
    this.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = color
    }
}

fun Int.getTime(): String {
    val second = this / 1000
    val min = second / 60
    val sec = second % 60
    return if (min > 0) {
        "$min min $sec sec"
    } else {
        "$sec second"
    }
}

fun Long.getRelativeTime(): RelativeTime {
    val second = this / 1000
    val minute = second / 60
    val hours = minute / 60
    val days = hours / 24
    val months = days / 30
    val year = months / 12
    if (year > 0) {
        return RelativeTime(year, "year")
    } else {
        if (months > 0) {
            return RelativeTime(months, "month")
        } else {
            if (days > 0) {
                return RelativeTime(days, "day")
            } else {
                if (hours > 0) {
                    return RelativeTime(hours, "hour")
                } else {
                    if (minute > 0) {
                        return RelativeTime(minute, "minute")
                    } else {
                        return RelativeTime(second, "second")
                    }
                }
            }
        }

    }
}

data class RelativeTime(val value: Long, val type: String)

fun Context.createMultiPart(keyName: String, photoPath: String): MultipartBody.Part {
    val file = File(photoPath)
    val compressedImageFile = Compressor(this).compressToFile(file)
    val requestFile = RequestBody.create(
        MediaType.parse("image/jpg"),
        compressedImageFile
    )

    // MultipartBody.Part is used to send also the actual file name
    return MultipartBody.Part.createFormData(keyName, compressedImageFile.name, requestFile)

}

fun String.getMultipartFormString(): RequestBody {
    return RequestBody.create(
        okhttp3.MultipartBody.FORM, this
    )
}