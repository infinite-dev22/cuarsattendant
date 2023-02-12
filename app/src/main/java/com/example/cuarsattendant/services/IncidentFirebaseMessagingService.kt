package com.example.cuarsattendant.services

import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.cuarsattendant.dataStore
import com.example.cuarsattendant.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

val CHANNEL_ID = "0"

class IncidentFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data.isNotEmpty()) {
            message.data.let {
                Log.d("CloudMessage", "Message Notification Body: ${it["body"]}")
            }
            if (message.notification != null) {
                var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(message.notification!!.title)
                    .setContentText(message.notification!!.body)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                Log.d("CloudMessage", "Notificatio: ${ message.notification }")
                Log.d("CloudMessage", "Message Notificatio title: ${ message.notification!!.title }")
                Log.d("CloudMessage", "Message Notificatio Body: ${ message.notification!!.body }")

            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        GlobalScope.launch {
            saveGCMToken(token)
        }
    }

    private suspend fun saveGCMToken(token: String) {
        val gckTokenKey = stringPreferencesKey("gcm_key")

        baseContext.dataStore.edit { pref ->
            pref[gckTokenKey] = token
        }
    }
}