package com.example.app.features.messaging.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MessagingViewModel : ViewModel() {
    // Estado para el token FCM
    var fcmToken: String? = null
        private set

    // Obtener el token FCM
    suspend fun getToken(): String = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FIREBASE", "getInstanceId failed", task.exception)
                    continuation.resumeWithException(task.exception ?: Exception("Unknown error"))
                    return@addOnCompleteListener
                }
                val token = task.result
                Log.d("FIREBASE", "FCM Token: $token")
                continuation.resume(token ?: "")
            }
    }

    // Ejemplo de uso: obtener el token y guardarlo
    fun fetchFirebaseToken() {
        viewModelScope.launch {
            try {
                val token = getToken()
                fcmToken = token
                Log.d("FIREBASE", "Token obtenido en MessagingViewModel: $token")
                // Aquí puedes guardar el token, enviarlo al servidor, etc.
            } catch (e: Exception) {
                Log.e("FIREBASE", "Error obteniendo token", e)
            }
        }
    }
}

