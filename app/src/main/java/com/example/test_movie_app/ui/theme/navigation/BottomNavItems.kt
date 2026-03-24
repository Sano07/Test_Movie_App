package com.example.test_movie_app.ui.theme.navigation

import com.example.test_movie_app.R

sealed class BottomNavItems(val title : String, val iconId: Int, val route: String) {
    object Home : BottomNavItems(title = "Movies", iconId = R.drawable.ic_home_item, route = "home_screen" )
    object Favorite : BottomNavItems(title = "Favorite",iconId = R.drawable.ic_favorite_item, route = "favorite_screen" )
}