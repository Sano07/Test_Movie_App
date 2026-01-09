package com.example.test_movie_app.ui.theme.navigation

import com.example.test_movie_app.R

sealed class BottomNavItems(val title : String, val iconId: Int, route: String) {
    object Home : BottomNavItems("Home", R.drawable.ic_home_item, "home_screen" )
    object Favorite : BottomNavItems("Favorite", R.drawable.ic_favorite_item, "favorite_screen" )
}