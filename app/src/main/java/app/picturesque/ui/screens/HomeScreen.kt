package app.picturesque.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import app.picturesque.R
import app.picturesque.data.model.PhotoItem
import app.picturesque.ui.navigation.Screens
import app.picturesque.ui.navigation.TopBar
import app.picturesque.ui.theme.Purple
import app.picturesque.ui.theme.sourceSansPro
import app.picturesque.ui.viewmodel.ImageViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeScreen(
    trendingImages: LazyPagingItems<PhotoItem>,
    footballImages: LazyPagingItems<PhotoItem>,
    basketballImages: LazyPagingItems<PhotoItem>,
    viewModel: ImageViewModel,
    navHostController: NavHostController,
) = with(viewModel) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = { TopBar(title = stringResource(R.string.app_name)) },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(50.dp),
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.nike_sneakers),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = sourceSansPro,
                ),
                modifier = Modifier.padding(start = 10.dp)
            )

            if (basketballImages.itemSnapshotList.isNotEmpty()) {
                LazyRow {
                    items(basketballImages) { image ->
                        if (image != null) {
                            ImageItem(image) {
                                setImageDetail(image)
                                navHostController.navigate(Screens.Details.route)
                            }
                        }
                    }
                }
            } else {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        color = Purple,
                        strokeWidth = 5.dp,
                    )
                }
            }

            Text(
                text = stringResource(R.string.national_parks),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = sourceSansPro
                ),
                modifier = Modifier.padding(start = 10.dp)
            )

            if (footballImages.itemSnapshotList.isNotEmpty()) {
                LazyRow {
                    items(footballImages) { image ->
                        if (image != null) {
                            ImageItem(image) {
                                setImageDetail(image)
                                navHostController.navigate(Screens.Details.route)
                            }
                        }
                    }
                }
            } else {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        color = Purple,
                        strokeWidth = 5.dp,
                    )
                }
            }

            Text(
                text = stringResource(R.string.trending),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = sourceSansPro
                ),
                modifier = Modifier.padding(start = 10.dp)
            )



            if (trendingImages.itemSnapshotList.isNotEmpty()) {
                LazyRow {
                    items(trendingImages) { image ->
                        ImageItem(image!!) {
                            setImageDetail(image)
                            navHostController.navigate(Screens.Details.route)
                        }
                    }
                }
            } else {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        color = Purple,
                        strokeWidth = 5.dp,
                    )
                }
            }
            Spacer(modifier = Modifier.height(65.dp))
        }
    }
}

@Composable
fun ImageItem(data: PhotoItem, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .size(150.dp)
            .padding(horizontal = 5.dp),
        elevation = 10.dp
    ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }) {

            GlideImage(
                imageModel = data.url,
                contentScale = ContentScale.Crop,
                placeHolder = Icons.Rounded.Image,
            )
        }
    }
}



