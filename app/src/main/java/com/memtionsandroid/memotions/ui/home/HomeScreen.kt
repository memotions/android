package com.memtionsandroid.memotions.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.memtionsandroid.memotions.data.remote.response.PostResponse
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme
import com.memtionsandroid.memotions.utils.DataResult

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {

    val emotions by viewModel.emotionsState.collectAsStateWithLifecycle()
    val post by viewModel.postState.collectAsStateWithLifecycle()
    val authToken by viewModel.authTokenState.collectAsStateWithLifecycle()

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
            when (post) {
                is DataResult.Error -> {
                    val postErrorMessage = (post as DataResult.Error).error.getContentIfNotHandled()
                    postErrorMessage?.let {
                        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
                    }
                }
                is DataResult.Loading -> {
                    CircularProgressIndicator()
                }
                is DataResult.Success -> {
                    HomeScreenContent(
                        items = (emotions as DataResult.Success<List<Emotion>>).data,
                        post = (post as DataResult.Success<PostResponse>).data,
                        authToken = authToken,
                        onAdd = { viewModel.addEmotion(it) },
                        onReplace = { viewModel.setAuthToken(it) },
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Composable
internal fun HomeScreenContent(
    items: List<Emotion>,
    post: PostResponse,
    authToken: String?,
    onAdd: (desc: String) -> Unit,
    onReplace: (token: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
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

        Text(
            "Post Response",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Text(text = post.title)
        Text(text = post.body)

        var newAuthToken by remember { mutableStateOf(TextFieldValue("")) }

        Text(
            "Auth Token",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Text(text = authToken ?: "No Auth Token")

        TextField(
            value = newAuthToken,
            onValueChange = { newAuthToken = it },
            label = { Text("Replace Auth Token") },
            modifier = Modifier.padding(top = 16.dp)
        )

        Button(
            onClick = {
                if (newAuthToken.text.isNotEmpty()) {
                    onReplace(newAuthToken.text)
                    newAuthToken = TextFieldValue("")
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Replace Auth Token")
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

        val samplePost = PostResponse(
            id = 1,
            title = "Sample Post",
            body = "This is a sample post body.",
            userId = 1
        )

        val sampleAuthToken = "Contoh Token 12123"

        HomeScreenContent(sampleEmotions, samplePost, sampleAuthToken, onAdd = {}, onReplace = {})
    }
}