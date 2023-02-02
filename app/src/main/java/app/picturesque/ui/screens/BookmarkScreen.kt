package app.picturesque.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import app.picturesque.R
import app.picturesque.data.model.PhotoItem
import app.picturesque.ui.navigation.Screens
import app.picturesque.ui.navigation.TopBar
import app.picturesque.ui.theme.Red
import app.picturesque.ui.viewmodel.ImageViewModel
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    viewModel: ImageViewModel,
    navHostController: NavHostController,
    scaffoldState: ScaffoldState,
) =
    with(viewModel) {
        var selectedImage by remember { mutableStateOf(PhotoItem(id = "", url = "", title = "")) }
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        var openDialog by remember { mutableStateOf(false) }

        if (openDialog) {
            AlertDialog(
                onDismissRequest = {
                    openDialog = false
                },
                title = {
                    Text(text = stringResource(R.string.remove_photo))
                },
                text = {
                    Text(
                        stringResource(R.string.deletion_message_bookmarks)
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (selectedImage.id.isNotEmpty())
                                removeImageFromFavorites(selectedImage)
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    context.getString(R.string.image_removed),
                                    context.getString(R.string.undo),
                                    SnackbarDuration.Long
                                ).also {
                                    if (it == SnackbarResult.ActionPerformed) {
                                        addImageToFavorites(selectedImage)
                                    }
                                }
                            }
                            openDialog = false
                        }
                    ) {
                        Text(stringResource(R.string.confirm), color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog = false
                        }
                    ) {
                        Text(stringResource(R.string.dismiss), color = Red)
                    }
                },

                )
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    title = stringResource(R.string.appbar_text_bookmarks),
                )
            }
        ) {

            Column(
                modifier = Modifier.fillMaxSize().padding(it),
                verticalArrangement = Arrangement.Center
            ) {

                favoriteImagesState.images?.let { photos ->

                    if (photos.isNotEmpty())
                        StaggeredGridView(images = photos, onImageClick = { photo ->
                            setImageDetail(photo)
                            navHostController.navigate(Screens.Details.route)
                        }, onHoldClick = { photo ->
                            selectedImage = photo
                            openDialog = true
                        }
                        )
                    else
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.empty_bookmarks_text),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }

                } ?: Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.empty_bookmarks_text),
                    )
                }
            }
        }

    }