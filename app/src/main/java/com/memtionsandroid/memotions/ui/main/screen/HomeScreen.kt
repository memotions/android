package com.memtionsandroid.memotions.ui.main.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.components.home.HomeTopBar
import com.memtionsandroid.memotions.ui.components.main.Journal
import com.memtionsandroid.memotions.ui.components.main.Journals
import com.memtionsandroid.memotions.ui.theme.CustomColors
import com.memtionsandroid.memotions.ui.theme.customColors

val journalList = listOf(
    Journal(
        "Hari yang sangat melelahkan hingga saya ingin membunuh semua orang",
        "bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla",
        "Date 1",
        listOf("Sekolah", "Kerja", "Belajar")
    ),
    Journal(
        "Sangat bahagia karena mendapatkan hadiah besar",
        "Hari ini saya merasa sangat beruntung karena mendapatkan hadiah yang saya inginkan sejak lama. Semoga terus membawa keberuntungan.",
        "Date 2",
        listOf("Liburan", "Hadiah"),
        true
    ),
    Journal(
        "Merasakan banyak tantangan dalam pekerjaan",
        "Pekerjaan hari ini sangat menantang dan penuh tekanan. Tapi saya belajar banyak hal baru.",
        "Date 3",
        listOf("Kerja", "Belajar")
    ),
    Journal(
        "Pagi yang cerah dan menyenangkan",
        "Cuaca hari ini sangat cerah dan menyenangkan. Saya merasa sangat berenergi untuk memulai hari ini.",
        "Date 4",
        listOf("Sekolah", "Olahraga"),
        true
    ),
    Journal(
        "Hujan deras sepanjang hari",
        "Hujan turun sangat deras hari ini, membuat saya lebih banyak menghabiskan waktu di rumah sambil menikmati secangkir teh hangat.",
        "Date 5",
        listOf("Cuaca", "Relaksasi")
    ),
    Journal(
        "Mendapatkan ide baru untuk proyek besar",
        "Saya mendapatkan ide yang sangat menarik untuk proyek besar yang sedang saya kerjakan. Semoga bisa membawa hasil yang luar biasa.",
        "Date 6",
        listOf("Kerja", "Proyek")
    ),
    Journal(
        "Hari yang sangat sibuk dengan banyak rapat",
        "Hari ini penuh dengan rapat dan diskusi penting. Rasanya sangat sibuk, tapi saya berhasil menyelesaikan banyak tugas.",
        "Date 7",
        listOf("Kerja", "Rapat")
    ),
    Journal(
        "Menghabiskan waktu berkualitas bersama keluarga",
        "Hari ini saya menghabiskan waktu bersama keluarga, bermain game dan makan malam bersama. Rasanya sangat bahagia.",
        "Date 8",
        listOf("Keluarga", "Waktu Bersama")
    ),
    Journal(
        "Bekerja keras untuk mencapai tujuan",
        "Saya merasa sangat fokus hari ini, bekerja keras untuk mencapai tujuan yang sudah saya tetapkan. Semoga bisa segera tercapai.",
        "Date 9",
        listOf("Kerja", "Tujuan")
    ),
    Journal(
        "Hari yang penuh dengan inspirasi",
        "Saya merasa sangat terinspirasi hari ini, banyak belajar dari orang-orang sekitar saya. Semoga bisa mengaplikasikan ilmu baru ini.",
        "Date 10",
        listOf("Belajar", "Inspirasi")
    )
)


@Composable
fun HomeScreen() {
    val customColors = MaterialTheme.customColors
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Tambahkan aksi saat FAB diklik
                    Log.d("FAB", "Floating Action Button Clicked!")
                },
                containerColor = customColors.barColor,
                contentColor = customColors.onBarColor,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pen), // Ganti dengan ikon FAB
                    contentDescription = "Add"
                )
            }
        },
        topBar = {
            HomeTopBar()
        },
//        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
//                    .padding(bottom = 80.dp)
            )
            {
                Journals(journalList)
            }
        }
    )
}




