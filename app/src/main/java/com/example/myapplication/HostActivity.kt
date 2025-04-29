package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.breeds.BreedsScreen
import com.example.myapplication.ui.gallery.GalleryScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            MyApplicationTheme {
                Scaffold(
                    topBar = { Toolbar(navController) }
                ) { padding ->

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Breeds.route,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable(route = Screen.Breeds.route) {
                            BreedsScreen(
                                modifier = Modifier,
                                navController = navController,
                            )
                        }
                        composable(
                            route = Screen.Gallery.route,
                            arguments = listOf(
                                navArgument("breed") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            GalleryScreen(
                                modifier = Modifier,
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    TopAppBar(
        title = {
            Text(
                text = when (navBackStackEntry?.destination?.route.orEmpty()) {
                    Screen.Breeds.route -> stringResource(R.string.breeds_screen_title)
                    else -> stringResource(R.string.gallery_screen_title)
                }
            )
        },
        navigationIcon = if (navBackStackEntry?.destination?.route.orEmpty() != Screen.Breeds.route) {
            {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        } else {
            { null }
        }
    )
}

sealed class Screen(val route: String) {
    data object Breeds : Screen("breeds")
    data object Gallery : Screen("gallery/{breed}") {
        fun createRoute(breed: String) = route.replace("{breed}", breed)
    }
}
