package app.picturesque.data.repository

import app.picturesque.data.local.PicturesqueDatabase
import app.picturesque.data.model.MetadataResponse
import app.picturesque.data.model.PhotoItem
import app.picturesque.data.model.SearchResponse
import app.picturesque.data.model.TrendingPhotosResponse
import app.picturesque.data.remote.ApiResponse
import app.picturesque.data.remote.FlickrAPI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: FlickrAPI,
    private val db: PicturesqueDatabase
) : Repository {

    override suspend fun searchPhotos(
        query: String,
        page: Int?,
        perPage: Int?
    ): ApiResponse<SearchResponse> {
        return try {
            val data = api.searchImages(query, page = page, perPage = perPage)
            ApiResponse.Success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse.Error(e.message ?: "An unknown error has occurred")
        }
    }

    override suspend fun getPhotoMetaData(photoId: String): ApiResponse<MetadataResponse> {
        return try {
            val data = api.getPhotoMetadata(photoId)
            ApiResponse.Success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse.Error(e.message.toString())
        }
    }

    override suspend fun getPopularPhotos(
        page: Int?,
        perPage: Int?
    ): ApiResponse<TrendingPhotosResponse> {
        return try {
            val data = api.fetchTrendingImages(page = page, perPage = perPage)
            ApiResponse.Success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse.Error(e.message.toString())
        }
    }

    override fun getFavoriteImages(): Flow<List<PhotoItem>> {
        return db.imageDao.getImages()
    }

    override suspend fun addImageToFavorites(image: PhotoItem) {
        return db.imageDao.insertImage(image)
    }

    override suspend fun removeImageFromFavorites(image: PhotoItem) {
        return db.imageDao.removeImage(image)
    }
}