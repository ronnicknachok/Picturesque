package app.picturesque.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.picturesque.R
import app.picturesque.data.model.Exif
import app.picturesque.ui.navigation.TopBar
import app.picturesque.ui.theme.Purple
import app.picturesque.ui.theme.sourceSansPro
import app.picturesque.ui.viewmodel.ImageViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DetailScreen(
    navHostController: NavHostController,
    viewModel: ImageViewModel,
) = with(viewModel) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    imageDetail.image?.let { photoItem ->
        val shareIntent = Intent(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_TEXT, photoItem.url)
            .putExtra(Intent.EXTRA_SUBJECT, photoItem.title)
            .setType("text/plain")

        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(R.string.detail_screen_title),
                    onBackPressed = { navHostController.navigateUp() },
                    actions = {
                        IconButton(onClick = {
                            context.startActivity(
                                Intent.createChooser(
                                    shareIntent,
                                    context.getString(R.string.share_using)
                                )
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Share,
                                contentDescription = stringResource(R.string.share_image),
                                tint = Color.Black
                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround,
            ) {

                Text(
                    text = photoItem.title,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontFamily = sourceSansPro
                    ),
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp)
                )

                GlideImage(
                    imageModel = photoItem.url,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                        .clickable {
                            uriHandler.openUri(photoItem.url)
                        }
                )

                IconButton(onClick = {
                    if (favoriteImagesState.images?.contains(photoItem) == true) {
                        removeImageFromFavorites(photoItem)
                    } else {
                        addImageToFavorites(photoItem)
                        Toast.makeText(
                            context,
                            context.getString(R.string.added_favorite),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    if (favoriteImagesState.images?.contains(photoItem) == true)
                        Icon(
                            imageVector = Icons.Rounded.Bookmark,
                            contentDescription = stringResource(R.string.add_favorite),
                            modifier = Modifier.size(50.dp),
                            tint = Color.Red
                        ) else {
                        Icon(
                            imageVector = Icons.Rounded.BookmarkBorder,
                            contentDescription = stringResource(R.string.add_favorite),
                            modifier = Modifier.size(50.dp),
                            tint = Color.Red
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.data),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontFamily = sourceSansPro
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
                )

                if (metadataState.loading) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator(
                            color = Purple,
                            strokeWidth = 5.dp,

                            )
                    }
                } else {
                    metadataState.metadata?.let { data ->
                        if (data.exif.isNotEmpty()) {
                            LazyColumn(
                                contentPadding = PaddingValues(bottom = 70.dp),
                                modifier = Modifier
                                    .height(350.dp)
                                    .padding(16.dp)
                            ) {
                                items(data.exif) { exif ->
                                    MetaDataItem(data = exif)
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp),
                                        color = Purple.copy(0.4f)
                                    )

                                }

                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .padding(vertical = 20.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = stringResource(R.string.no_metadata))
                            }
                        }
                    } ?: Column(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = stringResource(R.string.no_metadata))
                    }
                }
                Spacer(modifier = Modifier.height(65.dp))
            }
        }
    }
}

@Composable
fun MetaDataItem(data: Exif, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Text(text = data.label + ":", maxLines = 1, modifier = Modifier.padding(end = 5.dp))
        Text(text = data.raw._content)
    }
}