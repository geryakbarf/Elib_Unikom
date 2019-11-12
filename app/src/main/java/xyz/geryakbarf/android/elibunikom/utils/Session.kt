package xyz.geryakbarf.android.elibunikom.utils

import android.content.Context
import android.content.SharedPreferences

class Session(val context: Context) {

    private val PREFS_NAMES = "xyz.geryakbarf.elib"
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAMES, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }

    fun isLogin(KEY_NAME: String, value: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(KEY_NAME, value)
        editor.commit()
    }

    fun getValueString(KEY_NAME: String): String? {
        return sharedPreferences.getString(KEY_NAME, null)
    }

    fun getValueBoolean(KEY_NAME: String): Boolean? {
        return sharedPreferences.getBoolean(KEY_NAME, false)
    }
}