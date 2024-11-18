package com.memtionsandroid.memotions.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memtionsandroid.memotions.data.local.entity.Emotion
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme
import com.memtionsandroid.memotions.utils.DataResult

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {

    val emotions by viewModel.emotionsState.collectAsStateWithLifecycle()

    when (emotions) {
        is DataResult.Error -> {
            val errorMessage = (emotions as DataResult.Error).error.getContentIfNotHandled()
            errorMessage?.let {
                Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
            }
        }

        is DataResult.Loading -> {
            CircularProgressIndicator()
        }

        is DataResult.Success -> {
            HomeScreenContent(
                items = (emotions as DataResult.Success<List<Emotion>>).data,
                onAdd = { viewModel.addEmotion(it) },
                modifier = modifier
            )
        }
    }
}

@Composable
internal fun HomeScreenContent(
    items: List<Emotion>,
    onAdd: (desc: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            "Emotions List",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        items.forEach { emotion ->
            Text(text = emotion.description)
        }

        var newEmotion by remember { mutableStateOf(TextFieldValue("")) }

        TextField(
            value = newEmotion,
            onValueChange = { newEmotion = it },
            label = { Text("Add New Emotion") },
            modifier = Modifier.padding(top = 16.dp)
        )

        Button(
            onClick = {
                if (newEmotion.text.isNotEmpty()) {
                    onAdd(newEmotion.text)
                    newEmotion = TextFieldValue("")
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add Emotion")
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MemotionsTheme {
        val sampleEmotions = listOf(
            Emotion(description = "Happy"),
            Emotion(description = "Sad"),
            Emotion(description = "Angry")
        )
        HomeScreenContent(sampleEmotions, onAdd = {})
    }
}