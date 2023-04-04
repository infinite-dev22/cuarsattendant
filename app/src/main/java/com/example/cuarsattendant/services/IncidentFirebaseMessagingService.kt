package com.example.cuarsattendant.services

import androidx.core.app.NotificationCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.cuarsattendant.dataStore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val CHANNEL_ID = "0"

class IncidentFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data.isNotEmpty()) {
            if (message.notification != null) {
                NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(message.notification!!.title)
                    .setContentText(message.notification!!.body).priority =
                    NotificationCompat.PRIORITY_DEFAULT
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
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