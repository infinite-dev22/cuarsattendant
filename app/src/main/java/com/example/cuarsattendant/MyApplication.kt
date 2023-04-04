package com.example.cuarsattendant

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

// Singleton instance of Datastore preference
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "AttendantPreference")

class MyApplication : Application()