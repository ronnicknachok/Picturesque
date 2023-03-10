package app.picturesque.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import app.picturesque.R
import app.picturesque.ui.screens.DetailScreen
import app.picturesque.ui.screens.FavoriteScreen
import app.picturesque.ui.screens.HomeScreen
import app.picturesque.ui.screens.SearchScreen
import app.picturesque.ui.theme.sourceSansPro
import app.picturesque.ui.viewmodel.ImageViewModel
import app.picturesque.util.ConnectionState
import app.picturesque.util.connectivityState

@Composable
fun SetUpNavGraph(
    navHostController: NavHostController,
    viewModel: ImageViewModel
) {
    val context = LocalContext.current
    val connection by connectivityState()
    val scaffoldState = rememberScaffoldState()
    val isConnected = connection === ConnectionState.Available
    val popularImages = viewModel.popularImagesPager.collectAsLazyPagingItems()
    val filter1 = viewModel.searchImagePager1.collectAsLazyPagingItems()
    val filter2 = viewModel.searchImagePager2.collectAsLazyPagingItems()

    LaunchedEffect(key1 = isConnected) {
        filter1.refresh()
        filter2.refresh()
        popularImages.refresh()

        if (!isConnected) {
            scaffoldState.snackbarHostState.showSnackbar(
                context.getString(R.string.offline_text),
                context.getString(R.string.dismiss).uppercase(),
                SnackbarDuration.Long
            )
        } else {
            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        }
    }

    Scaffold(bottomBar = { BottomNavBar(navHostController) }, scaffoldState = scaffoldState) {
        NavHost(
            navController = navHostController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(it),
        ) {
            composable(route = Screens.Home.route) {
                HomeScreen(
                    trendingImages = popularImages,
                    footballImages = filter1,
                    basketballImages = filter2,
                    viewModel = viewModel,
                    navHostController = navHostController,
                )
            }
            composable(route = Screens.Bookmarks.route) {
                FavoriteScreen(
                    viewModel, navHostController, scaffoldState
                )
            }
            composable(route = Screens.Search.route) {
                SearchScreen(
                    viewModel, navHostController
                )
            }
            composable(route = Screens.Details.route) {
                DetailScreen(
                    navHostController, viewModel
                )
            }
        }
    }
}


@Composable
fun BottomNavBar(
    navController: NavHostController = rememberNavController(),
) {
    val screens = listOf(
        Screens.Home,
        Screens.Search,
        Screens.Bookmarks,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        modifier = Modifier
            .height(70.dp)
            .padding(8.dp)
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(
                    topStart = 40.dp,
                    topEnd = 40.dp,
                    bottomEnd = 40.dp,
                    bottomStart = 40.dp
                )
            },
        elevation = 10.dp,
        backgroundColor = Color.Black,

        ) {

        screens.forEach {
            this@BottomNavigation.AddItem(
                screens = it,
                currentDestination = currentDestination,
                navController = navController
            )
        }

    }

}

@Composable
fun RowScope.AddItem(
    screens: Screens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screens.title)
        },
        onClick = {
            navController.navigate(screens.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = { Icon(imageVector = screens.icon, contentDescription = null) },
        selected = currentDestination?.hierarchy?.any { it.route == screens.route } == true,
        selectedContentColor = Color.White,
        unselectedContentColor = Color.White.copy(ContentAlpha.disabled),
    )
}


@Composable
fun TopBar(
    title: String,
    onBackPressed: (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = sourceSansPro,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        navigationIcon = if (onBackPressed != null) {
            {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(Icons.Rounded.ArrowBack, stringResource(R.string.back_icon))
                }
            }
        } else null,
        backgroundColor = Color.White,
        contentColor = Color.Black,
        elevation = 10.dp,
        actions = {
            if (actions != null) {
                actions()
            }
        },
    )
}
