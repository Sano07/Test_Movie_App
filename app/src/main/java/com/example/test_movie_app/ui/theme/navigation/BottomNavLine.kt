package com.example.test_movie_app.ui.theme.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun BottomNavLine(selectedItem: MutableState<String>, navController : NavController) {

    val listItems = listOf(BottomNavItems.Home, BottomNavItems.Favorite)

    NavigationBar {
        // перебор списка іtem-ов для отображения в нав баре
        listItems.forEach { item ->
            NavigationBarItem(
                selected = selectedItem.value == item.title,
                onClick = {
                    selectedItem.value = item.title
                    navController.navigate(item.route)

                },
                icon = {
                    Icon(painter = painterResource(id = item.iconId), contentDescription = "Icon Logo")
                },
                label = {
                    //Text(text = item.title, fontSize = 15.sp)
                },
                colors = NavigationBarItemDefaults.colors(),
            )
        }
    }
}