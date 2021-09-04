package com.fernandolerma.compose_example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fernandolerma.compose_example.models.BottomNavigationItem

class MainActivity : ComponentActivity() {
    @Composable
    private fun AppBar() {
        TopAppBar(
            title = { Text("AppBar") },
            navigationIcon = {
                IconButton(onClick = {

                }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            },
            actions = {
                IconButton(onClick = {

                }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }
        )
    }

    @Composable
    private fun ContentPage(navController: NavHostController) {
        NavHost(navController, startDestination = "Home") {
            composable("Home") {
                Column {
                    Text(text = "Home")
                    Text(text = ":0")
                }
            }
            composable("Fav") {
                Column {
                    Text(text = "Fav")
                    Text(text = ":0")
                }
            }
        }
    }

    @Composable
    private fun BottomNavigatorBar(navController: NavController) {
        val items = listOf(
            BottomNavigationItem(title = "Home", icon = R.drawable.ic_home, route = "Home"),
            BottomNavigationItem(title = "Fav", icon = R.drawable.ic_home, route = "Fav")
        )
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.design_default_color_primary),
            contentColor = Color.White,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    },
                    label = { Text(text = item.title) },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { AppBar() },
                    bottomBar = { BottomNavigatorBar(navController = navController) }) {
                    ContentPage(navController = navController)
                }
            }
        }
    }
}