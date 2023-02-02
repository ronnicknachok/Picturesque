package app.picturesque.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import app.picturesque.data.model.ImageState
import app.picturesque.data.model.PhotoItem
import app.picturesque.data.paging.SearchImageDataSource
import app.picturesque.data.paging.TrendingImagesDataSource
import app.picturesque.data.remote.ApiResponse
import app.picturesque.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    init {
        getFavorites()
    }

    var queryImageState by mutableStateOf(ImageState())
        private set

    var favoriteImagesState by mutableStateOf(ImageState())

    var imageDetail by mutableStateOf(ImageState())
        private set

    var metadataState by mutableStateOf(ImageState())
        private set


    val popularImagesPager = Pager(
        PagingConfig(pageSize = 100)
    ) {
        TrendingImagesDataSource(repository)
    }.flow.cachedIn(viewModelScope)

    val searchImagePager1 = Pager(
        PagingConfig(pageSize = 100)
    ) {
        SearchImageDataSource(repository, "national_parks")
    }.flow.cachedIn(viewModelScope)

    val searchImagePager2 = Pager(
        PagingConfig(pageSize = 100)
    ) {
        SearchImageDataSource(repository, "nike_jordan")
    }.flow.cachedIn(viewModelScope)

    fun setImageDetail(image: PhotoItem) {
        imageDetail = imageDetail.copy(image = image)
        getPhotoMetadata(image.id)
    }

    fun searchImages(query: String) = viewModelScope.launch {
        queryImageState = queryImageState.copy(
            loading = true,
        )

        when (val searchResponse = repository.searchPhotos(query)) {
            is ApiResponse.Error -> {
                queryImageState = queryImageState.copy(
                    error = searchResponse.message
                )
            }
            is ApiResponse.Success -> {
                val images = ArrayList<PhotoItem>()
                searchResponse.data?.let {
                    Log.e("resp", it.photos.page.toString())
                    it.photos.photo.forEach { image ->
                        Log.e("image", image.id)
                        val imageObject = PhotoItem(
                            id = image.id,
                            url = "https://farm${image.farm}.staticflickr.com/${image.server}/${image.id}_${image.secret}.jpg",
                            title = image.title
                        )
                        images.add(imageObject)
                    }
                }
                queryImageState = queryImageState.copy(
                    images = images,
                    loading = false,
                )
            }
        }
    }

    private fun getPhotoMetadata(photoId: String) = viewModelScope.launch {
        metadataState = metadataState.copy(
            loading = true,
        )

        metadataState = when (val result = repository.getPhotoMetaData(photoId)) {
            is ApiResponse.Error -> {
                metadataState.copy(
                    error = result.message.toString(),
                )
            }
            is ApiResponse.Success -> {
                metadataState.copy(
                    metadata = result.data?.photo,
                    loading = false
                )
            }
        }
    }

    private fun getFavorites() = viewModelScope.launch {
        repository.getFavoriteImages().collect {
            favoriteImagesState = favoriteImagesState.copy(images = it)
        }
    }

    fun addImageToFavorites(image: PhotoItem) = viewModelScope.launch {
        repository.addImageToFavorites(image)
    }

    fun removeImageFromFavorites(image: PhotoItem) = viewModelScope.launch {
        repository.removeImageFromFavorites(image)
    }
}


