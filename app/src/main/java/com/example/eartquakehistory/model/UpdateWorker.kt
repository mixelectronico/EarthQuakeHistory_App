package com.example.eartquakehistory.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.eartquakehistory.R
import com.example.eartquakehistory.view.MainActivity

class UpdateWorker(context: Context, params: WorkerParameters): Worker(context, params) {

    private var mContext = context
    val repository = EventRepository(mContext)

    override fun doWork(): Result {
        repository.getEventList()
        return Result.success()
    }
}