package app.picturesque.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.picturesque.ui.navigation.SetUpNavGraph
import app.picturesque.ui.theme.ImageGalleyAppTheme
import app.picturesque.ui.viewmodel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageGalleyAppTheme {
                navHostController = rememberNavController()
                val viewModel = hiltViewModel<ImageViewModel>()
                SetUpNavGraph(navHostController, viewModel)
            }
        }
    }
}
