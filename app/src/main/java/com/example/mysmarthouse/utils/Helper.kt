package com.example.mysmarthouse.utils

import android.util.Log
import java.nio.charset.StandardCharsets
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class Helper {
    companion object {
        fun getTime(): Long {
            return System.currentTimeMillis()
        }

        fun stringToSign(signUrl: String, method: String = "GET"): String {
            return listOf(
                method,
                contentHash(),
                "",
                signUrl
            ).joinToString(separator = "\n")
        }

        private fun contentHash(): String {
            val messageDigest = MessageDigest.getInstance("SHA-256")
            val hashBytes = messageDigest.digest("".toByteArray(StandardCharsets.UTF_8))
            return hashBytes.joinToString("") { "%02x".format(it) }
        }

        fun sign(clientId: String, secret: String, t: String, accessToken: String?, nonce: String?, stringToSign: String): String {
            val sb = StringBuilder()
            sb.append(clientId)
            if (accessToken != null) {
                sb.append(accessToken)
            }
            sb.append(t)
            if (nonce != null) {
                sb.append(nonce)
            }
            sb.append(stringToSign)
            Log.d("Main Activity", sb.toString())
            return sha256HMAC(sb.toString(), secret)
        }

        private fun sha256HMAC(data: String, secret: String): String {
            val algorithm = "HmacSHA256"
            return try {
                val hmacKey = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), algorithm)
                val mac = Mac.getInstance(algorithm)
                mac.init(hmacKey)
                val hmacData = mac.doFinal(data.toByteArray(StandardCharsets.UTF_8))
                bytesToHex(hmacData)
            } catch (e: NoSuchAlgorithmException) {
                throw IllegalArgumentException("Unknown algorithm: $algorithm", e)
            } catch (e: InvalidKeyException) {
                throw IllegalArgumentException("Invalid secret key", e)
            }
        }

        private fun bytesToHex(bytes: ByteArray): String {
            val hexChars = CharArray(bytes.size * 2)
            for (i in bytes.indices) {
                val v = bytes[i].toInt() and 0xFF
                hexChars[i * 2] = "0123456789ABCDEF"[v.ushr(4)]
                hexChars[i * 2 + 1] = "0123456789ABCDEF"[v and 0x0F]
            }
            return String(hexChars)
        }
    }
}