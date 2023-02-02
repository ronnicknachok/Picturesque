package app.picturesque.data.repository

import app.picturesque.data.model.MetadataResponse
import app.picturesque.data.model.PhotoItem
import app.picturesque.data.model.SearchResponse
import app.picturesque.data.model.TrendingPhotosResponse
import app.picturesque.data.remote.ApiResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getFavoriteImages(): Flow<List<PhotoItem>>
    suspend fun addImageToFavorites(image: PhotoItem)
    suspend fun removeImageFromFavorites(image: PhotoItem)

    suspend fun getPhotoMetaData(
        photoId: String
    ): ApiResponse<MetadataResponse>

    suspend fun searchPhotos(
        query: String,
        page: Int? = null,
        perPage: Int? = null
    ): ApiResponse<SearchResponse>

    suspend fun getPopularPhotos(
        page: Int? = null,
        perPage: Int? = null
    ): ApiResponse<TrendingPhotosResponse>
}
