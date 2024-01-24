package com.ys.macdonaldclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ys.macdonaldclone.ui.details.DetailsScreen
import com.ys.macdonaldclone.ui.home.HomeScreen
import com.ys.macdonaldclone.ui.menu.MenuScreen
import com.ys.macdonaldclone.ui.theme.MacdonaldCloneTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MacdonaldCloneTheme(darkTheme = false) {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable(route = "home") {
                        HomeScreen(
                            onCategoryClick = { categoryId ->
                                // categoryId argument 전달
                                navController.navigate("menu?/$categoryId")
                            },
                            onMenuItemClick = {
                                // categoryId argument 없이 navigate 를 하면 composable 에 등록된 arguments의 defaultValue 에 의해서 기본 값을 자동 설정 ex) menu/0 으로 설정
                                navController.navigate("menu")
                            },
                        )
                    }
                    composable(
                        // ?을 추가해서 categoryId 을 옵셔널로 받을수 있도록 허용
                        route = "menu?/{categoryId}",
                        arguments = listOf(navArgument("categoryId") {
                            type = NavType.LongType
                            // default 값을 설정하지 않으면 categoryId 를 옵셔널로 사용할수 없음
                            defaultValue = 0
                        })
                    ) { backStackEntry ->
                        MenuScreen(
                            categoryId = backStackEntry.arguments?.getLong("categoryId"),
                            onBackClick = { navController.navigateUp() },
                            onMenuItemClick = { menuItemId ->
                                navController.navigate("details/$menuItemId")
                            }
                        )
                    }
                    composable(
                        route = "details/{menuItemId}",
                        arguments = listOf(navArgument("menuItemId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        DetailsScreen(
                            menuItemId = backStackEntry.arguments!!.getLong("menuItemId"),
                            onBackClick = { navController.navigateUp() },
                        )
                    }
                }
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