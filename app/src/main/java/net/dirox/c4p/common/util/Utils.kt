package net.dirox.c4p.common.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import net.dirox.c4p.BuildConfig
import net.dirox.c4p.R
import net.dirox.c4p.common.DateTimeConstants
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import kotlin.math.min


object Utils {


    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    fun dpToPx(context: Context, dip: Float): Float {
        val r = context.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            r.displayMetrics
        )
    }

    fun getDateFromString(
        dateStr: String,
        pattern: String = DateTimeConstants.dateTimeFormatUS
    ): Date? {
        if (dateStr.isEmpty()) return null
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.parse(dateStr)
    }

    fun stringFromDate(
        date: Date,
        pattern: String = DateTimeConstants.dateTimeFormatUS
    ): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(date)
    }


    @SuppressLint("SimpleDateFormat")
    fun showDateFromReturnCalendarActivity(string: String): String {
        val spf = SimpleDateFormat("MM/dd/yyyy")
        val spf2 = SimpleDateFormat("dd/MM/yyyy")
        return spf.format(spf2.parse(string))
    }

    @SuppressLint("SimpleDateFormat")
    fun showDateFromReturnBackUp(string: String): String {
        try {
            val spf = SimpleDateFormat("dd/MM/yyyy")
            val spf2 = SimpleDateFormat("MM/dd/yyyy")
            return spf.format(spf2.parse(string))
        }catch (ex: Exception){
            return ""
        }
    }

    fun convertStringDateWithPatternUS(date: Date, pattern: String = "MM/dd/yyyy"): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(date)
    }


    fun dateFromString(
        string: String,
        pattern: String = DateTimeConstants.defaultDateTimeFormat
    ): Date? {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return try {
            sdf.parse(string)
        } catch (e: Exception) {
            null
        }
    }

    fun apiStringFromDate(date: Date): String {
        return stringFromDate(
            date,
            DateTimeConstants.apiDateTimeFormat
        )
    }

    fun dateFromApiString(string: String): Date? {
        return dateFromString(
            string,
            DateTimeConstants.apiDateTimeFormat
        )
    }

    fun formatNumber(double: Double, minimumFractionDigits: Int = 2): String {
        val nf = NumberFormat.getCurrencyInstance(Locale.US)
        nf.maximumFractionDigits = 2
        nf.minimumFractionDigits = minimumFractionDigits
        return nf.format(double)
    }

    fun formatNumber2(double: Double): String {
        val nf = NumberFormat.getNumberInstance()
        nf.maximumFractionDigits = 2
        nf.minimumFractionDigits = 0
        return nf.format(double)
    }

    fun simpleDouble(value: Double?): String {
        val nonNullValue = value ?: 0.0
        if (nonNullValue == nonNullValue.toInt().toDouble()) {
            return nonNullValue.toInt().toString()
        }
        return nonNullValue.toString()
    }

    @SuppressLint("SimpleDateFormat")
    public fun parseFormatDate(string: String): String {
        val spf = SimpleDateFormat("MMM dd, yyyy 'at' HH:mm aaa")
        val spf2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return spf.format(spf2.parse(string))
    }

    @SuppressLint("SimpleDateFormat")
    public fun parseFormatDateExport(string: String): String {
        val spf = SimpleDateFormat("MMM dd, yyyy")
        val spf2 = SimpleDateFormat("dd/MM/yyyy")
        return spf.format(spf2.parse(string))
    }

    @SuppressLint("SimpleDateFormat")
    public fun parseFormatDateRequestExport(string: String): String {
        val spf = SimpleDateFormat("yyyy-MM-dd")
        val spf2 = SimpleDateFormat("dd/MM/yyyy")
        return spf.format(spf2.parse(string))
    }

    @SuppressLint("SimpleDateFormat")
    fun parseFormatDate2(string: String): String {
        val spf = SimpleDateFormat("MMM dd, yyyy 'at' hh:mm aaa")
        val spf2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ")
        return spf.format(spf2.parse(string))
    }

    @SuppressLint("SimpleDateFormat")
    fun getDayAgoFromTimeDonor(day: String): String {
        val apiDateTimeFormat = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(apiDateTimeFormat)
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        val past = try {
            sdf.parse(day)
        } catch (e: Exception) {
            Date()
        }
        val now = Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
        val days = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

        if (seconds < 60) {
            return seconds.toString() + " seconds ago"
        } else if (minutes < 60) {
            return minutes.toString() + " minutes ago"
        } else if (hours < 24) {
            return hours.toString() + " hours ago"
        } else {
            return days.toString() + " days ago"
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getDayAgoFromTime(day: String): String {
        val apiDateTimeFormat = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
        val sdf = SimpleDateFormat(apiDateTimeFormat)
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        val past = try {
            sdf.parse(day)
        } catch (e: Exception) {
            Date()
        }
        val now = Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
        val days = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

        if (seconds < 60) {
            return seconds.toString() + " seconds ago"
        } else if (minutes < 60) {
            return minutes.toString() + " minutes ago"
        } else if (hours < 24) {
            return hours.toString() + " hours ago"
        } else {
            return days.toString() + " days ago"
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getExpireDateAgo(day2: String): String {
        val apiDateTimeFormat = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
        val sdf = SimpleDateFormat(apiDateTimeFormat)
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        val past = Date()
        val now = try {
            sdf.parse(day2)
        } catch (e: Exception) {
            return "-"
        }
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
        val days = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

        if (seconds < 60) {
            if (seconds < 0) {
                val secondsPast = TimeUnit.MILLISECONDS.toSeconds(past.time - now.time)
                val minutesPast = TimeUnit.MILLISECONDS.toMinutes(past.time - now.time)
                val hoursPast = TimeUnit.MILLISECONDS.toHours(past.time - now.time)
                val daysPast = TimeUnit.MILLISECONDS.toDays(past.time - now.time)
                if (secondsPast < 60) {
                    if (secondsPast < 0) {
                        return "Ended"
                    } else {
                        return "Ended " + secondsPast.toString() + " seconds ago"
                    }
                } else if (minutesPast < 60) {
                    return "Ended " + minutesPast.toString() + " minutes ago"
                } else if (hoursPast < 24) {
                    return "Ended " + hoursPast.toString() + " hours ago"
                } else {
                    return "Ended " + daysPast.toString() + " days ago"
                }
            } else {
                return seconds.toString() + " seconds to go"
            }
        } else if (minutes < 60) {
            return minutes.toString() + " minutes to go"
        } else if (hours < 24) {
            return hours.toString() + " hours to go"
        } else {
            return days.toString() + " days to go"
        }
    }

    fun shareLinkToEmail(activity: Activity, subject: String, message: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, message)
        emailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(Intent.createChooser(emailIntent, "Choose an action"))
    }

    fun shareLinkToSMS(activity: Activity, message: String?) {
        val uri = Uri.parse("smsto:")
        val smsIntent = Intent(Intent.ACTION_SENDTO, uri)
        smsIntent.putExtra("sms_body", message)
        smsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(smsIntent)
    }

    fun copyLink(activity: Activity, id: String, code: String?) {
        val clipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var clip: ClipData? = null
        if (code != null)
            clip = ClipData.newPlainText("link", BuildConfig.LINK_SHARE_HOST + id + "/?ref=" + code)
        else clip = ClipData.newPlainText("link", BuildConfig.LINK_SHARE_HOST + id)
        clipboard.setPrimaryClip(clip!!)
        Toast.makeText(activity, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
    }

    fun copyTextToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var clip: ClipData = ClipData.newPlainText("id_code", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
    }


    private fun getBitmapFromView(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap
    }

    fun makeTextViewResizable(tv: TextView, maxLine: Int, expandText: String, viewMore: Boolean) {
        if (tv.tag == null) {
            tv.tag = tv.text
        }
        val vto = tv.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                val text: String
                val lineEndIndex: Int
                val obs = tv.viewTreeObserver
                obs.removeGlobalOnLayoutListener(this)
                if (maxLine == 0) {
                    lineEndIndex = tv.layout.getLineEnd(0)
                    text = tv.text.subSequence(
                        0,
                        lineEndIndex - expandText.length + 1
                    ).toString() + "... " + expandText
                } else if (maxLine > 0 && tv.lineCount >= maxLine) {
                    lineEndIndex = tv.layout.getLineEnd(maxLine - 1)
                    text = tv.text.subSequence(
                        0,
                        lineEndIndex - expandText.length + 1
                    ).toString() + "... " + expandText
                } else {
                    lineEndIndex = tv.layout.getLineEnd(tv.layout.lineCount - 1)
                    text = tv.text.subSequence(0, lineEndIndex).toString() + " " + expandText
                }
                tv.text = text
                tv.movementMethod = LinkMovementMethod.getInstance()
                tv.setText(
                    addClickablePartTextViewResizable(
                        Html.fromHtml(tv.text.toString()),
                        tv,
                        lineEndIndex,
                        expandText,
                        viewMore
                    ), TextView.BufferType.SPANNABLE
                )
            }
        })

    }

    private fun addClickablePartTextViewResizable(
        strSpanned: Spanned,
        tv: TextView,
        maxLine: Int,
        spanableText: String,
        viewMore: Boolean
    ): SpannableStringBuilder {

        val str = strSpanned.toString()
        val ssb = SpannableStringBuilder(strSpanned)
        if (str.contains(spanableText)) {
            ssb.setSpan(object : MySpannable(false) {
                override fun onClick(widget: View) {
                    tv.layoutParams = tv.layoutParams
                    tv.setText(tv.tag.toString(), TextView.BufferType.SPANNABLE)
                    tv.invalidate()
                    if (!viewMore) {
                        makeTextViewResizable(
                            tv,
                            maxLine,
                            "read more",
                            true
                        )
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length, 0)

        }
        return ssb

    }

    @SuppressLint("SimpleDateFormat")
    fun parseFormatDateParticipantDetail(string: String): String {
        val spf = SimpleDateFormat("MMM dd, yyyy")
        val spf2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ")
        return spf.format(spf2.parse(string))
    }

    @SuppressLint("SimpleDateFormat")
    fun parseFormatDateClaimBeta(string: String): String {
        val spf = SimpleDateFormat("MMM dd, yyyy")
        val spf2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return spf.format(spf2.parse(string))
    }

    @SuppressLint("SimpleDateFormat")
    fun parseFormatDateClaim(string: String): String {
        val spf = SimpleDateFormat("MMM dd, yyyy")
        val spf2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ")
        return spf.format(spf2.parse(string))
    }

    @SuppressLint("SimpleDateFormat")
    fun parseFormatDateFromPointDetail(string: String): String {
        val spf = SimpleDateFormat("MMM dd, yyyy")
        val spf2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ")
        return spf.format(spf2.parse(string))
    }

    fun getCountDownTime(ms: Long): String {
        val totalSeconds = ms / 1000
        val seconds = totalSeconds % 60
        val totalMinutes = totalSeconds / 60
        val minutes = totalMinutes % 60
        val totalHours = totalMinutes / 60
        val hours = totalHours % 24
        val totalDays = totalHours / 24

        var timeString = String.format("%dh %dm %ds", hours, minutes, seconds)
        if (totalDays > 0) {
            timeString = "$totalDays ${if (totalDays == 1L) "day" else "days"} $timeString"
        }
        return timeString
    }

    public fun makeTextBoldAndColor(
        context: Context,
        sentence: String,
        word: String,
        textView: TextView
    ) {
        val builder = SpannableStringBuilder()
        val startIndex = 0
        val endIndex = sentence.length
        val spannableString = SpannableString(word)
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(
            boldSpan,
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        ) //To make text Bold
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorGray)),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        ) //To change color of text
        builder.append(spannableString)
        textView.setText(builder, TextView.BufferType.SPANNABLE)
    }

    public fun makeTextBoldAndColor2(
        context: Context,
        sentence: String,
        word: String,
        startIndex: Int,
        textView: TextView
    ) {
        val builder = SpannableStringBuilder()
        val spannableString = SpannableString(word)
        val boldSpan = StyleSpan(Typeface.BOLD)
        val endIndex = startIndex + sentence.length
        spannableString.setSpan(
            boldSpan,
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        ) //To make text Bold
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorGray)),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        ) //To change color of text
        builder.append(spannableString)
        textView.setText(builder, TextView.BufferType.SPANNABLE)
    }

    public fun calculateTotalByCard(amount: Double): Double {
        // Fee: 2.9% + 30¢
        return (amount + 0.3) / (1 - 2.9 / 100)
    }

    public fun calculateTotalByBank(amount: Double): Double {
        // Fee: 0.80%, capped at $5
        return Math.min(amount / (1 - 0.8 / 100), amount + 5)
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDay(): String {
        val currentDate = Calendar.getInstance().getTime()
        val df = SimpleDateFormat("dd/MM/yyyy")
        return df.format(currentDate)
    }

    fun getYoutubeID(url: String): String? {
        var vId: String? = null
        val pattern = Pattern.compile("((?<=(v|V)/)|(?<=be/)|(?<=(\\?|\\&)v=)|(?<=embed/))([\\w-]++)",
        Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(url)
        if (matcher.find()) {
            vId = matcher.group()
        }
        return vId
    }

    fun formatPhoneNumber(s: String): String {
        val unformattedPhone = getUnformatedPhoneNumber(s)

        var result = ""
        result += String.format("(%s", unformattedPhone.substring(0, min(3, unformattedPhone.length)))
        if (unformattedPhone.length >= 3) {
            result += String.format(") %s", unformattedPhone.substring(3, min(6, unformattedPhone.length)))
        }
        if (unformattedPhone.length >= 6) {
            result += String.format("-%s", unformattedPhone.substring(6, unformattedPhone.length))
        }

        if (result == "(") {
            return ""
        }
        return result
    }

    fun getUnformatedPhoneNumber(s: String): String {
        return s.replace("(", "")
            .replace(")", "")
            .replace("-", "")
            .replace(" ", "")
    }

    fun getUnformatedPhoneNumber2(s: String): String {
        return s.replace("(", "")
            .replace(")", "")
            .replace(" ", "")
    }

    fun isLocationModeEnabled(context: Context): Boolean {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gps_enabled = false

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(ex : Exception ) {
            return false
        }
        return gps_enabled
    }
}