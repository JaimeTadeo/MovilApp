package com.example.app.features.notification.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.example.app.R

class FirebaseService : FirebaseMessagingService() {
    companion object {
        val TAG = FirebaseService::class.java.simpleName
    }

//    override fun onNewToken(token: String) {
//        Log.d(TAG, "Refreshed token: $token")
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // FCM registration token to your app server.
//        sendRegistrationToServer(token)
//    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.from)
        var title = "Nuevo mensaje"
        var body = "Tienes un nuevo mensaje."
        // Si el mensaje tiene datos personalizados
        if (remoteMessage.data.isNotEmpty()) {
            body = remoteMessage.data["body"] ?: body
            title = remoteMessage.data["title"] ?: title
        }
        // Si el mensaje tiene payload de notificación
        remoteMessage.notification?.let {
            title = it.title ?: title
            body = it.body ?: body
        }
        sendNotification(title, body)
    }

    private fun sendNotification(title: String, messageBody: String) {
        val channelId = "default_channel_id"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Crear canal de notificación para Android O+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Mensajes",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal para mensajes push"
                enableLights(true)
                lightColor = Color.BLUE
            }
            notificationManager.createNotificationChannel(channel)
        }
        // Usar un icono seguro
        val iconRes = try { R.drawable.ic_stat_ic_notification } catch (e: Exception) { android.R.drawable.ic_dialog_info }
        // Usar color seguro
        val colorRes = try { getColor(R.color.colorAccent) } catch (e: Exception) { Color.BLUE }
        Log.d(TAG, "Mostrando notificación: $title - $messageBody")
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(iconRes)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setColor(colorRes)
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }
}
