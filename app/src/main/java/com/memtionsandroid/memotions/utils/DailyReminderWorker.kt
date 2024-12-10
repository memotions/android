package com.memtionsandroid.memotions.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.MainActivity

class DailyReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val notificationManager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Pengingat Jurnal Harian",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Pengingat untuk menulis jurnal setiap hari"
                enableVibration(true)
                enableLights(true)
            }

            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationContent = getRandomJournalReminder()

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("Memotions")
            .setContentText(notificationContent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)

        return Result.success()
    }

    private fun getRandomJournalReminder(): String {
        return journalReminderMessages.random()
    }

    companion object {
        const val CHANNEL_ID = "daily_journal_reminder"
        const val NOTIFICATION_ID = 1001

        val journalReminderMessages = listOf(
            "Yuk, tuliskan perasaanmu hari ini!",
            "Momen spesial hari ini belum terabadikan, ayo tulis di jurnal!",
            "Jurnal adalah cermin diri. Sudahkah kamu menulis hari ini?",
            "Setiap pikiran dan perasaan layak untuk diabadikan. Ayo tulis!",
            "Refleksikan hari ini melalui tulisan. Jurnal menunggumu!",
            "Pengalaman hari ini akan menjadi kenangan indah. Tuliskan segera!",
            "Waktunya berbagi cerita dengan diri sendiri. Ayo update jurnalmu!",
            "Satu paragraf bisa mengubah perspektifmu. Mulai menulis!",
            "Jurnal adalah teman setia yang selalu mendengarkan. Curahan hatimu!",
            "Rekam momen berhargamu hari ini. Jangan sampai terlupakan!",
            "Apa yang membuatmu tersenyum hari ini? Tuliskan di jurnal!",
            "Luangkan waktu sejenak untuk dirimu sendiri. Yuk, menulis jurnal!",
            "Ceritakan pengalamanmu hari ini. Sederhana, tapi berarti!",
            "Jurnalmu adalah tempat terbaik untuk memulai perjalanan refleksi.",
            "Kisah hari ini adalah bagian dari perjalananmu. Yuk, catat sekarang!",
            "Membuat jurnal adalah hadiah kecil untuk diri di masa depan.",
            "Setiap hari adalah cerita baru. Jangan lewatkan untuk menulisnya!",
            "Ceritakan apa yang membuatmu bangga hari ini. Jurnalmu menanti!",
            "Simpan momen berharga hari ini di jurnal. Kamu pasti bangga nanti!",
            "Apa yang kamu pelajari hari ini? Catat di jurnal dan temukan maknanya!",
        )
    }
}