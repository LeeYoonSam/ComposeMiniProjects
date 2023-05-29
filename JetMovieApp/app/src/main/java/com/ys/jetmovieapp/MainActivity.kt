package com.ys.jetmovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ys.jetmovieapp.ui.theme.JetMovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetMovieAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Magenta
                    ),
                    title = { Text(text = "Movies") }
                )
            }
        ) { paddingValues ->
            content()
        }
    }
}

@Composable
fun MainContent() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Text(text = "Hello")
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MyApp {
        MainContent()
    }
}