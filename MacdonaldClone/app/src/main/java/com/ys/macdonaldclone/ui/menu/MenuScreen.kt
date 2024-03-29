package com.ys.macdonaldclone.ui.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ys.macdonaldclone.model.Category
import com.ys.macdonaldclone.model.Menu
import com.ys.macdonaldclone.ui.components.Icon
import com.ys.macdonaldclone.ui.theme.MacdonaldCloneTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun MenuScreen(
    categoryId: Long? = null,
    onBackClick: () -> Unit,
    onMenuItemClick: (Long) -> Unit,
    viewModel: MenuViewModel = viewModel()
) {
    val data by viewModel.data.observeAsState(Menu(emptyList(), emptyList()))

    val lazyListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        categoryId?.run {
            val startCategory = data.categories.firstOrNull { it.id == this }
            startCategory?.getIndex(data)?.let { selectedCategoryIndex ->
                coroutineScope.launch {
                    lazyListState.scrollToItem(selectedCategoryIndex)
                }
            }
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "McDonald's Menu") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Rounded.ArrowBack)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            Column {
                CategoryTabs(
                    categories = data.categories,
                    selectedCategory = lazyListState.firstVisibleItemIndex.getCategory(data),
                    onCategorySelected = { category ->
                        coroutineScope.launch { lazyListState.scrollToItem(category.getIndex(data)) }
                    }
                )
                Divider()
                LazyColumn(
                    state = lazyListState,
                ) {
                    data.categories.forEach { category ->  
                        item {
                            Text(
                                text = category.name,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        val menuItems = data.menuItems.filter { it.categoryId == category.id }
                        itemsIndexed(menuItems) { index, menuItem ->
                            MenuItem(
                                menuItem = menuItem,
                                onClick = { onMenuItemClick(menuItem.id) },
                                onIncrementMenuItemQuantity = { viewModel.incrementMenuItemQuantity(menuItem) },
                                onDecrementMenuItemQuantity = { viewModel.decrementMenuItemQuantity(menuItem) },
                            )
                            if (index != menuItems.lastIndex)
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }

            AnimatedVisibility(
                visible = data.menuItems.any { it.quantity > 0 },
                enter = slideInVertically(
                    initialOffsetY = { it * 2 }
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it * 2 }
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                CartButton(
                    quantity = data.menuItems.sumOf { it.quantity },
                    price = data.menuItems.filter { it.quantity > 0 }.sumOf { it.price },
                    onClick = {}
                )
            }
        }
    }
}

private fun Int.getCategory(menu: Menu): Category {
    return menu.categories.last { it.getIndex(menu) <= this }
}

private fun Category.getIndex(menu: Menu): Int {
    var index = 0
    for (i in 0 until menu.categories.indexOf(this)) {
        index += 1
        index += menu.menuItems.filter { it.categoryId == menu.categories[i].id }.size
    }
    return index
}

@ExperimentalAnimationApi
@Preview("MenuScreen")
@Composable
private fun MenuScreenPreview() {
    MacdonaldCloneTheme {
        MenuScreen(
            onBackClick = {},
            onMenuItemClick = {}
        )
    }
}

@ExperimentalAnimationApi
@Preview("MenuScreen • Dark")
@Composable
private fun MenuScreenDarkPreview() {
    MacdonaldCloneTheme(darkTheme = true) {
        MenuScreen(
            onBackClick = {},
            onMenuItemClick = {}
        )
    }
}