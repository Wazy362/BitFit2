package com.example.bitfit1

import android.app.Application

class BitFitApplication : Application() {
    val db: AppDatabase by lazy { AppDatabase.getInstance(this) }
}