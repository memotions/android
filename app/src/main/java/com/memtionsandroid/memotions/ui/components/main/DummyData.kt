package com.memtionsandroid.memotions.ui.components.main

import com.memtionsandroid.memotions.data.local.entity.EmotionAnalysis
import com.memtionsandroid.memotions.data.local.entity.Journal


data class TagDummy(
    val name: String
)

val tagsJurnal = listOf(
    TagDummy(name = "Refleksi Harian"),
    TagDummy(name = "Rasa Syukur"),
    TagDummy(name = "Pelacakan Mood"),
    TagDummy(name = "Catatan Impian"),
    TagDummy(name = "Target Harian"),
    TagDummy(name = "Kesehatan Mental"),
    TagDummy(name = "Produktivitas"),
    TagDummy(name = "Perenungan"),
    TagDummy(name = "Momen Bahagia"),
)



val dummyEmotionAnalysis = listOf(
    EmotionAnalysis(emotion = "happy", confidence = 0.85f),
    EmotionAnalysis(emotion = "neutral", confidence = 0.65f)
)

val dummyJournal = Journal(
    id = 1,
    userId = 123,
    title = "A Day in the Life",
    content = "Today was a good day. I learned new things and felt happy throughout.",
    datetime = "2024-12-07T10:30:00",
    createdAt = "2024-12-07T09:00:00",
    status = "DRAFT",
    deleted = false,
    starred = true,
    feedback = "Keep up the good work!",
    tags = listOf("personal", "motivation", "learning"),
    emotionAnalysis = dummyEmotionAnalysis
)

// Membuat list dengan beberapa salinan dummyJournal yang memiliki ID berbeda
val dummyList = listOf(
    dummyJournal.copy(id = 1),
    dummyJournal.copy(id = 2, status = "PUBLISHED"),
    dummyJournal.copy(id = 3)
)


data class JournalDummy(
    val title: String,
    val content: String,
    val date: String,
    val Tags: List<String>,
    val emotion: String,
    val isStarred: Boolean,
    val status: String = "Published"
)


val journalList = listOf(
    JournalDummy(
        title = "Awal Perjalanan",
        content = "Hari ini adalah awal dari perjalanan baru dalam hidup saya. Saya merasa seperti membuka lembaran baru yang penuh dengan harapan. Meski ada rasa takut akan ketidakpastian, saya berusaha melihat setiap langkah sebagai peluang untuk belajar dan berkembang. Semoga perjalanan ini membawa banyak hal baik.",
        date = "2024-12-01",
        Tags = listOf("perjalanan", "kehidupan"),
        emotion = "happy",
        isStarred = true,
        status = "Published"
    ),
    JournalDummy(
        title = "Kerja Keras",
        content = "Hari ini penuh dengan tantangan di tempat kerja. Banyak tugas yang harus diselesaikan dalam waktu singkat, membuat saya harus bekerja ekstra keras. Meski melelahkan, ada kepuasan tersendiri saat semua berhasil saya selesaikan. Saya sadar bahwa terkadang kesulitan membawa pelajaran yang berharga.",
        date = "2024-12-01",
        Tags = listOf("kerja", "tantangan"),
        emotion = "neutral",
        isStarred = false
    ),
    JournalDummy(
        title = "Hujan Sepanjang Hari",
        content = "Sejak pagi hingga malam, hujan terus turun tanpa henti. Suasana di luar rumah terasa sunyi, hanya terdengar suara tetesan air. Hujan membuat hati saya sedikit melankolis, mengingatkan pada kenangan lama yang tiba-tiba muncul. Namun, saya mencoba menikmatinya sambil bersantai di rumah.",
        date = "2024-12-01",
        Tags = listOf("cuaca", "hujan"),
        emotion = "sad",
        isStarred = false,
        status = "Draft"
    ),
    JournalDummy(
        title = "Malam Tenang",
        content = "Setelah hari yang panjang, akhirnya saya bisa menikmati malam yang tenang. Saya duduk di ruang tamu sambil membaca buku favorit dan menikmati segelas teh hangat. Waktu seperti ini membuat saya merasa damai dan bersyukur atas momen kecil yang berharga dalam hidup.",
        date = "2024-11-30",
        Tags = listOf("malam", "buku"),
        emotion = "neutral",
        isStarred = true
    ),
    JournalDummy(
        title = "Pencapaian Baru",
        content = "Hari ini saya berhasil menyelesaikan proyek yang sudah lama saya kerjakan. Rasanya luar biasa melihat hasil kerja keras selama ini akhirnya membuahkan hasil. Ini bukan hanya tentang pencapaian itu sendiri, tetapi juga proses panjang yang penuh dengan pelajaran dan pengalaman berharga.",
        date = "2024-12-01",
        Tags = listOf("pencapaian", "kerja"),
        emotion = "angry",
        isStarred = true
    ),
    JournalDummy(
        title = "Hari Biasa",
        content = "Tidak ada yang istimewa hari ini, hanya menjalani rutinitas seperti biasanya. Meski terasa monoton, saya berusaha menemukan hal kecil yang bisa disyukuri, seperti secangkir kopi hangat di pagi hari atau udara segar saat berjalan pulang. Hidup memang tidak selalu menarik, tapi tetap berharga.",
        date = "2024-12-01",
        Tags = listOf("rutinitas", "biasa"),
        emotion = "neutral",
        isStarred = false
    ), JournalDummy(
        title = "Hari Biasa",
        content = "Tidak ada yang istimewa hari ini, hanya menjalani rutinitas seperti biasanya. Meski terasa monoton, saya berusaha menemukan hal kecil yang bisa disyukuri, seperti secangkir kopi hangat di pagi hari atau udara segar saat berjalan pulang. Hidup memang tidak selalu menarik, tapi tetap berharga.",
        date = "2023-12-01",
        Tags = listOf("rutinitas", "biasa"),
        emotion = "neutral",
        isStarred = false
    ), JournalDummy(
        title = "Hari Biasa",
        content = "Tidak ada yang istimewa hari ini, hanya menjalani rutinitas seperti biasanya. Meski terasa monoton, saya berusaha menemukan hal kecil yang bisa disyukuri, seperti secangkir kopi hangat di pagi hari atau udara segar saat berjalan pulang. Hidup memang tidak selalu menarik, tapi tetap berharga.",
        date = "2023-12-01",
        Tags = listOf("rutinitas", "biasa"),
        emotion = "scared",
        isStarred = false
    ), JournalDummy(
        title = "Hari Biasa",
        content = "Tidak ada yang istimewa hari ini, hanya menjalani rutinitas seperti biasanya. Meski terasa monoton, saya berusaha menemukan hal kecil yang bisa disyukuri, seperti secangkir kopi hangat di pagi hari atau udara segar saat berjalan pulang. Hidup memang tidak selalu menarik, tapi tetap berharga.",
        date = "2024-09-01",
        Tags = listOf("rutinitas", "biasa"),
        emotion = "scared",
        isStarred = false,
        status = "Draft"
    ),
    JournalDummy(
        title = "Waktu Bersama Keluarga",
        content = "Hari ini saya menghabiskan waktu bersama keluarga. Kami makan malam bersama, berbagi cerita, dan tertawa lepas. Momen seperti ini membuat saya merasa sangat bahagia dan bersyukur atas kehadiran orang-orang terdekat dalam hidup saya. Tidak ada yang lebih berharga dari keluarga.",
        date = "2024-11-28",
        Tags = listOf("keluarga", "bahagia"),
        emotion = "happy",
        isStarred = true,
        status = "Draft"
    ),
    JournalDummy(
        title = "Refleksi Diri",
        content = "Saya meluangkan waktu untuk merenungkan perjalanan hidup saya sejauh ini. Ada banyak keputusan yang membawa saya ke titik ini, beberapa baik, beberapa tidak. Tetapi dari semua itu, saya belajar bahwa setiap pengalaman, baik atau buruk, memiliki makna dan pelajaran yang bisa diambil.",
        date = "2024-11-29",
        Tags = listOf("refleksi", "kehidupan"),
        emotion = "neutral",
        isStarred = false,
        status = "Published"
    ),
    JournalDummy(
        title = "Hari yang Sibuk",
        content = "Hari ini penuh dengan jadwal yang padat dan pekerjaan yang menumpuk. Saya hampir tidak punya waktu untuk istirahat. Meski melelahkan, ada kepuasan tersendiri saat melihat semua tugas selesai tepat waktu. Hari ini saya belajar pentingnya manajemen waktu yang lebih baik.",
        date = "2024-12-07",
        Tags = listOf("sibuk", "produktif"),
        emotion = "neutral",
        isStarred = false
    ),
    JournalDummy(
        title = "Hari Bahagia",
        content = "Hari ini saya mendapatkan kabar baik yang sudah lama saya nantikan. Rasanya seperti semua usaha saya selama ini terbayar lunas. Saya merasa sangat bahagia dan bersyukur atas kesempatan ini. Semoga ini menjadi awal dari banyak hal baik di masa depan.",
        date = "2024-12-08",
        Tags = listOf("bahagia", "kabarbaik"),
        emotion = "happy",
        isStarred = true
    )
)