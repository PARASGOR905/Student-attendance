package com.example.student.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.student.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Prefs @Inject constructor(
    @ApplicationContext context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs: SharedPreferences = if (BuildConfig.DEBUG) {
        // Use regular SharedPreferences in debug for easier debugging
        context.getSharedPreferences(
            Constants.PREF_NAME,
            Context.MODE_PRIVATE
        )
    } else {
        // Use encrypted SharedPreferences in release
        EncryptedSharedPreferences.create(
            context,
            Constants.PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // Authentication
    var isLoggedIn: Boolean
        get() = prefs.getBoolean(Constants.PREF_IS_LOGGED_IN, false)
        set(value) = prefs.edit { putBoolean(Constants.PREF_IS_LOGGED_IN, value) }

    // User data
    var userId: String?
        get() = prefs.getString(Constants.PREF_USER_ID, null)
        set(value) = prefs.edit { putString(Constants.PREF_USER_ID, value) }

    var userName: String?
        get() = prefs.getString(Constants.PREF_USER_NAME, null)
        set(value) = prefs.edit { putString(Constants.PREF_USER_NAME, value) }

    var userEmail: String?
        get() = prefs.getString(Constants.PREF_USER_EMAIL, null)
        set(value) = prefs.edit { putString(Constants.PREF_USER_EMAIL, value) }

    var userRole: String?
        get() = prefs.getString(Constants.PREF_USER_ROLE, null)
        set(value) = prefs.edit { putString(Constants.PREF_USER_ROLE, value) }

    // Helper methods
    fun clearUserData() {
        prefs.edit {
            remove(Constants.PREF_IS_LOGGED_IN)
            remove(Constants.PREF_USER_ID)
            remove(Constants.PREF_USER_NAME)
            remove(Constants.PREF_USER_EMAIL)
            remove(Constants.PREF_USER_ROLE)
        }
    }

    // Generic getter/setter for custom preferences
    inline fun <reified T> get(key: String, defaultValue: T): T {
        return when (T::class) {
            String::class -> prefs.getString(key, defaultValue as? String) as T
            Int::class -> prefs.getInt(key, (defaultValue as? Int) ?: 0) as T
            Boolean::class -> prefs.getBoolean(key, (defaultValue as? Boolean) ?: false) as T
            Long::class -> prefs.getLong(key, (defaultValue as? Long) ?: 0L) as T
            Float::class -> prefs.getFloat(key, (defaultValue as? Float) ?: 0f) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    inline fun <reified T> put(key: String, value: T) {
        prefs.edit {
            when (T::class) {
                String::class -> putString(key, value as String)
                Int::class -> putInt(key, (value as? Int) ?: 0)
                Boolean::class -> putBoolean(key, (value as? Boolean) ?: false)
                Long::class -> putLong(key, (value as? Long) ?: 0L)
                Float::class -> putFloat(key, (value as? Float) ?: 0f)
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }
    }

    fun contains(key: String): Boolean = prefs.contains(key)

    fun remove(key: String) {
        prefs.edit { remove(key) }
    }

    fun clearAll() {
        prefs.edit { clear() }
    }

    companion object {
        // Helper extension for SharedPreferences
        private inline fun SharedPreferences.edit(
            commit: Boolean = false,
            action: SharedPreferences.Editor.() -> Unit
        ) {
            val editor = edit()
            action(editor)
            if (commit) {
                editor.commit()
            } else {
                editor.apply()
            }
        }
    }
}
