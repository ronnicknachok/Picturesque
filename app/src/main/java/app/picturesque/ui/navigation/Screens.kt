package app.picturesque.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Details
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Home : Screens(route = "home_screen", title = "Home", icon = Icons.Rounded.Home)
    object Bookmarks :
        Screens(route = "bookmarks_screen", title = "Bookmarks", icon = Icons.Rounded.Bookmark)

    object Search : Screens(route = "search_screen", title = "Search", icon = Icons.Rounded.Search)
    object Details : Screens(route = "detail_screen", title = "Details", icon = Icons.Rounded.Details)
}