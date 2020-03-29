package com.wisilica.imsafe.data.local

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class SharedPreferencesUtility(private val mContext: Context) {

    /**
     * Setting a boolean key value limit Shared preference
     *
     * @param key
     * @param value
     */
    fun setDoublePrefValue(key: String, value: Double) {
        val pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()

        editor.putLong(key, java.lang.Double.doubleToRawLongBits(value))
        editor.apply()
    }

    fun getDouble(key: String): Double {

        val pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        return java.lang.Double.longBitsToDouble(
            pref.getLong(
                key,
                java.lang.Double.doubleToLongBits(0.0)
            )
        )
    }

    /**
     * Getting boolean key value fromDeviceCursor shared preference
     *
     * @param key
     * @return
     */
    fun getBooleanPrefValue(key: String): Boolean {
        val pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        return pref.getBoolean(key, false)
    }

    /**
     * Getting boolean key value fromDeviceCursor shared preference
     *
     * @param key
     * @return
     */
    fun getBooleanPrefValue2(key: String): Boolean {
        val pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        return pref.getBoolean(key, true)
    }

    /**
     * Getting boolean key value fromDeviceCursor shared preference
     *
     * @param key
     * @return
     */
    fun getLongPrefValue(key: String): Long? {
        val pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        var value: Long = 0
        try {
            value = pref.getLong(key, 0)
            if (0L == value) {
                return -1
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return value
    }

    /**
     * Setting a boolean key value limit Shared preference
     *
     * @param key
     * @param value
     */
    fun setBooleanPrefValue(key: String, value: Boolean) {
        val pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()

        editor.putBoolean(key, value)
        editor.commit()

    }

    /**
     * Getting float key value fromDeviceCursor shared preference
     *
     * @param key
     * @return
     */
    fun getFloatPrefValue(key: String): Float {
        val pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        return pref.getFloat(key, 0.000000.toFloat())
    }

    /**
     * Setting a boolean key value limit Shared preference
     *
     * @param key
     * @param value
     */
    fun setFloatPrefValue(key: String, value: Float) {
        val pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()

        editor.putFloat(key, value)
        editor.commit()

    }

    /**
     * Setting a boolean key value limit Shared preference
     *
     * @param key
     * @param value
     */
    fun setLongPrefValue(key: String, value: Long?) {
        val pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        var temp=value
        if(temp==null){
            temp=0
        }

        editor.putLong(key, temp)
        editor.commit()

    }

    /**
     * Getting boolean key value fromDeviceCursor shared preference
     *
     * @param key
     * @return
     */
    fun getStringPrefValue(key: String): String? {
        val pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        return pref.getString(key, null)
    }

    /**
     * Setting a boolean key value limit Shared preference
     *
     * @param key
     * @param value
     */
    fun setStringPrefValue(key: String, value: String?) {
        val pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()

        editor.putString(key, value)
        editor.commit()

    }

    /**
     * Getting integer key value fromDeviceCursor shared preference
     *
     * @param key
     * @return
     */
    fun getIntegerPrefValue(key: String): Int {
        val pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        return pref.getInt(key, -1)
    }

    /**
     * Setting a integer key value limit Shared preference
     *
     * @param key
     * @param value
     */
    fun setIntegerPrefValue(key: String, value: Int) {
        val pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()

        editor.putInt(key, value)
        editor.commit()
    }

    fun clear() {
        val settings = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        settings.edit().clear().commit()
    }

    object Constants {
        val APP_THEME="app_theme"

    }

    companion object {
        private val SHARED_PREF_NAME = "TopStories"
    }
}
