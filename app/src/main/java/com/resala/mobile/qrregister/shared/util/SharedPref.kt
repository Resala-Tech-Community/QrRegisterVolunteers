/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.util

import android.content.SharedPreferences


class SharedPref(private val pref: SharedPreferences) {

    private var editor: SharedPreferences.Editor = pref.edit()

    private fun putString(value: String?, key: Key) {
        editor.putString(key.name, value)
        editor.apply()
    }

    private fun getString(key: Key, def: String): String? {
        return pref.getString(key.name, def)
    }


    fun clear() {
        editor.clear()
        editor.apply()
    }

    enum class Key {
        SESSION,
        LANG,
        PREF_LANG
    }

    var session: String
        get() = getString(Key.SESSION, "")!!
        set(value) {
            putString(value, Key.SESSION)
        }
    var lang: String
        get() = getString(Key.LANG, "en")!!
        set(value) {
            putString(value, Key.LANG)
        }


}
