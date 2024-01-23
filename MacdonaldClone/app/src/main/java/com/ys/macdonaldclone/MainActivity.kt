package com.ys.macdonaldclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ys.macdonaldclone.ui.menu.MenuScreen
import com.ys.macdonaldclone.ui.theme.MacdonaldCloneTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MacdonaldCloneTheme {
//                HomeScreen(
//                    onCategoryClick = {},
//                    onMenuItemClick = {}
//                )
                MenuScreen(
                    onBackClick = {},
                    onMenuItemClick = {}
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MacdonaldCloneTheme {
        Greeting("Android")
    }
}