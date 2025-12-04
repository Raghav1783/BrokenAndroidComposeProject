package com.greedygame.brokenandroidcomposeproject.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun DetailScreen(modifier: Modifier = Modifier, articleId: Int?) {
    val viewModel: mainViewmodel = hiltViewModel()

    LaunchedEffect(articleId) {
        viewModel.getArticleDetail(articleId?:-1)
    }

    val uiState by viewModel.articleDetailUiState.collectAsStateWithLifecycle()



    when {
        uiState.isLoading -> {
            CircularProgressIndicator()
        }

        uiState.errorMessage != null -> {
            Text("Error: ${uiState.errorMessage}", color = Color.Red)
        }

        uiState.article != null -> {
            val article = uiState.article!!
            var editableContent by remember(article.title) { mutableStateOf(article.title ?: "") }

            Column(modifier = modifier
                .fillMaxSize()
                .padding(16.dp)) {
                Text(article.title)
                Text(article.author ?: "Unknown")

                OutlinedTextField(
                    value = editableContent,
                    onValueChange = { editableContent = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.headlineSmall
                )

                Button(
                    onClick = {
                        viewModel.updateArticle(
                            article.copy(
                                title = editableContent,
                                isSynced = false
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Changes")
                }
            }
        }

        else -> {
            Text("No article found")
        }
    }

}