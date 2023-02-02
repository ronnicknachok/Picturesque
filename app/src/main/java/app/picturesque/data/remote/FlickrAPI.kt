package app.picturesque.data.remote

import app.picturesque.BuildConfig
import app.picturesque.data.model.MetadataResponse
import app.picturesque.data.model.SearchResponse
import app.picturesque.data.model.TrendingPhotosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrAPI {

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&sort=interestingness-desc&api_key=$API_KEY")
    suspend fun searchImages(
        @Query("text") query: String? = null,
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int? = 100,
    ): SearchResponse

    @GET("?method=flickr.interestingness.getList&format=json&nojsoncallback=1&api_key=$API_KEY")
    suspend fun fetchTrendingImages(
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int? = 100,
    ): TrendingPhotosResponse

    @GET("?method=flickr.photos.getExif&format=json&nojsoncallback=1&api_key=$API_KEY")
    suspend fun getPhotoMetadata(
        @Query("photo_id") photoId: String
    ): MetadataResponse

    companion object {
        const val API_KEY = BuildConfig.FLICKR_API_KEY
    }

}
