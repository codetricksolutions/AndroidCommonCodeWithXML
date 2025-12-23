package com.cts.common.code.session

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties

class EncryptedSharedPrefs(
    context: Context,
    prefsName: String = "secure_prefs",
    private val keyAlias: String = "MySecureAESKey"
) {
    companion object {
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val AES_MODE = "AES/GCM/NoPadding"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    init {
        createKeyIfNeeded()
    }

    private fun encrypt(value: String): String {
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(value.toByteArray(Charsets.UTF_8))
        val combined = iv + encryptedBytes
        return Base64.encodeToString(combined, Base64.DEFAULT)
    }

    private fun decrypt(encrypted: String): String {
        val decoded = Base64.decode(encrypted, Base64.DEFAULT)
        val iv = decoded.copyOfRange(0, 12) // GCM IV length = 12 bytes
        val encryptedBytes = decoded.copyOfRange(12, decoded.size)
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), GCMParameterSpec(128, iv))
        return String(cipher.doFinal(encryptedBytes), Charsets.UTF_8)
    }

    fun putString(key: String, value: String) {
        prefs.edit().putString(key, encrypt(value)).apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putString(key, encrypt(value.toString())).apply()
    }

    fun putInt(key: String, value: Int) {
        prefs.edit().putString(key, encrypt(value.toString())).apply()
    }

    fun getString(key: String, defaultValue: String? = null): String? {
        val encrypted = prefs.getString(key, null) ?: return defaultValue
        return runCatching { decrypt(encrypted) }.getOrDefault(defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        val encrypted = prefs.getString(key, null) ?: return defaultValue
        return runCatching { decrypt(encrypted).toBooleanStrictOrNull() ?: defaultValue }
            .getOrDefault(defaultValue)
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        val encrypted = prefs.getString(key, null) ?: return defaultValue
        return runCatching { decrypt(encrypted).toIntOrNull() ?: defaultValue }
            .getOrDefault(defaultValue)
    }

    fun remove(key: String) = prefs.edit().remove(key).apply()

    fun clearAll() = prefs.edit().clear().apply()

    private fun createKeyIfNeeded() {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        if (!keyStore.containsAlias(keyAlias)) {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    keyAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(256)
                    .build()
            )
            keyGenerator.generateKey()
        }
    }

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        return keyStore.getKey(keyAlias, null) as SecretKey
    }
}