package uz.gita.dimaa.mymaxway.data.locale

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefImpl @Inject constructor(private val pref: SharedPreferences) : SharedPref {
    override var smsVerification: String
        set(value) { pref.edit().putString("SMS_VERIFICATION", value).apply() }
        get() = pref.getString("SMS_VERIFICATION", "").toString()
    override var name: String
        set(value) { pref.edit().putString("TOKEN", value).apply() }
        get() = pref.getString("TOKEN", "").toString()

    override var hasToken: Boolean
        set(value) { pref.edit().putBoolean("HAS_TOKEN", value).apply() }
        get() = pref.getBoolean("HAS_TOKEN", false)

    override var phone: String
        set(value) { pref.edit().putString("PHONE", value).apply() }
        get() = pref.getString("PHONE", "").toString()

}