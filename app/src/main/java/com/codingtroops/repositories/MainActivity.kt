package com.codingtroops.repositories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.codingtroops.repositories.ui.theme.RepositoriesAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RepositoriesAppTheme {
                val viewModel: RepositoriesViewModel = viewModel()
                val reposFow = viewModel.repositories
                val lazyRepoItems: LazyPagingItems<Repository> =
                    reposFow.collectAsLazyPagingItems()

                RepositoriesScreen(lazyRepoItems)
            }
        }
    }
}