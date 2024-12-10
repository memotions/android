package com.memtionsandroid.memotions.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
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

        if (!isAppInForeground()) {
            notificationManager.notify(NOTIFICATION_ID, notification)
        }

        return Result.success()
    }

    private fun getRandomJournalReminder(): String {
        return journalReminderMessages.random()
    }

    private fun isAppInForeground(): Boolean {
        val lifecycle = ProcessLifecycleOwner.get().lifecycle
        return lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
    }

    companion object {
        const val CHANNEL_ID = "daily_journal_reminder"
        const val NOTIFICATION_ID = 1001

        val journalReminderMessages = listOf(
            "Selamat pagi! Hari ini pasti penuh petualangan seru. Jangan lupa cerita nanti ya! ✨🔥",
            "Yo, hari baru nih! Bikin tiap detik jadi berkesan. Ceritain nanti di jurnal ya! 🌟📖",
            "Good vibes only hari ini! Semangat ya, dan jangan lupa cerita keseruanmu nanti ✍️🌈.",
            "Bangun, dunia menunggu aksimu hari ini! Cerita nanti bakal seru banget, yakin! 🚀✨",
            "Selamat pagi, pahlawan cerita! Hari ini bakal jadi epic, siap-siap share kisahnya nanti 🕶️📓.",
            "Hari ini bakal keren banget kalau kamu jalanin dengan semangat! Ceritain nanti ya! 💪⚡",
            "Good morning! Yuk, jalani hari dengan senyum dan cerita epic yang siap ditulis nanti 🌅📔.",
            "Semangat pagi! Hari ini adalah kesempatan baru buat bikin kisah seru. Jangan lupa share! ✨💭",
            "Halo, superstar! Hari ini bakal amazing, cerita nanti pasti bikin bangga 🌟🎇.",
            "Ayo bangkit, bikin harimu memorable! Ceritakan petualangan kerenmu nanti, ya 🌈📘.",
            "Bangun, pejuang mimpi! Siapkan harimu untuk jadi cerita yang layak diabadikan nanti 💕🚀.",
            "Selamat pagi! Jadilah legenda di harimu sendiri, dan pastikan kamu cerita nanti! 🏆📖",
            "Hari ini penuh peluang keren, jangan lupa semangat! Ceritakan semuanya nanti ✍️✨.",
            "Yo, the world is yours today! Jalani harimu, dan siapkan cerita hebat untuk nanti 🌟📓.",
            "Bangkit, dan tunjukkan siapa dirimu hari ini! Ceritakan kisah heroikmu nanti 😎✨.",
            "Pagi ini cerah, sama seperti potensi harimu! Jangan lupa cerita nanti ya! 🌄📒",
            "Awali hari dengan senyuman, jalani dengan semangat, dan ceritakan semua nanti 🌈📖.",
            "Semangat pagi! Setiap langkahmu hari ini bakal jadi bagian dari cerita seru nanti ✨🌟.",
            "Pagi ini istimewa karena kamu ada di dalamnya. Ceritakan petualanganmu nanti ya! 💪📘",
            "Hari baru, semangat baru! Jalani dengan penuh energi, dan share ceritanya nanti 🌟✍️."
        )

    }
}