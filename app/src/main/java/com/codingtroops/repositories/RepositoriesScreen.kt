package com.codingtroops.repositories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed


@Composable
fun RepositoriesScreen(repos: LazyPagingItems<Repository>) {
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = 8.dp
        )
    ) {
        itemsIndexed(repos) { idx, repo ->
            if (repo != null) {
                RepositoryItem(index = idx, item = repo)
            }
        }
        val refreshLoadState = repos.loadState.refresh
        val appendLoadState = repos.loadState.append
        when {
            refreshLoadState is LoadState.Loading -> {
                item {
                    LoadingItem(Modifier.fillParentMaxSize())
                }
            }
            refreshLoadState is LoadState.Error -> {
                val error = refreshLoadState.error
                item {
                    ErrorItem(
                        message = error.localizedMessage ?: "",
                        modifier = Modifier.fillParentMaxSize()
                    ) {
                        repos.retry()
                    }
                }
            }
            appendLoadState is LoadState.Loading -> {
                item {
                    LoadingItem(Modifier.fillMaxWidth())
                }
            }
            appendLoadState is LoadState.Error -> {
                val error = appendLoadState.error
                item {
                    ErrorItem(message = error.localizedMessage ?: "") {
                        repos.retry()
                    }
                }
            }
        }
    }
}

@Composable 
fun LoadingItem(modifier: Modifier = Modifier) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(24.dp)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorItem(message: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = message,
            maxLines = 2,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6,
            color = Color.Red
        )
        Button(onClick = onClick, modifier = Modifier.padding(8.dp)) {
            Text("Retry")
        }
    }
}

@Composable
fun RepositoryItem(index: Int, item: Repository) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .height(120.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = index.toString(),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .weight(0.2f)
                    .padding(8.dp))
            Column(modifier = Modifier.weight(0.8f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.h6)
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3)
            }
        }
    }
}