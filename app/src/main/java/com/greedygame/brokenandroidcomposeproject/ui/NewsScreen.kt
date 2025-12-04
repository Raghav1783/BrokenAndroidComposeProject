package com.greedygame.brokenandroidcomposeproject.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greedygame.brokenandroidcomposeproject.data.Article
import com.greedygame.brokenandroidcomposeproject.data.BrokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Composable
fun NewsScreen(
    onNavigateToDetail: (String) -> Unit
) {
    val viewModel: mainViewmodel = hiltViewModel()

    val state = viewModel.uiState.collectAsStateWithLifecycle()
//    var articles by remember { mutableStateOf<List<Article>>(emptyList()) }
//    var loading by remember { mutableStateOf(true) }
//    var error by remember { mutableStateOf<String?>(null) }

//    LaunchedEffect(Unit) {
//        GlobalScope.launch(Dispatchers.Main) {
//            try {
//                val result = BrokenRepository.fetchArticlesBlocking()
//                articles = result
//                loading = false
//            } catch (e: Exception) {
//                error = e.message
//                loading = false
//            }
//        }
//    }

    when {
        state.value.isLoading -> CircularProgressIndicator()
        state.value.errorMessage != null -> Text(state.value.errorMessage!!)
        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.value.articles) { article ->
                    Column(Modifier.clickable{onNavigateToDetail("item_detail/${article.id}")}) {

                        Text(article.title)
                        Text(article.author ?: "no author")
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }
        }
    }
//
//    if (loading) {
//        CircularProgressIndicator()
//        return
//    }
//
//
//    if (error != null) {
//        Text(text = "Error: $error")
//        return
//    }
//
//
//    if (articles.isEmpty()) {
//        Text(text = "No articles found")
//        return
//    }


}