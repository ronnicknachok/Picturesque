package app.picturesque.di

import android.app.Application
import androidx.room.Room
import app.picturesque.data.local.PicturesqueDatabase
import app.picturesque.data.remote.FlickrAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideImagesApi(): FlickrAPI {
        return Retrofit.Builder()
            .baseUrl("https://api.flickr.com/services/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }


    @Provides
    @Singleton
    fun provideImageDatabase(app: Application): PicturesqueDatabase {
        return Room.databaseBuilder(
            app,
            PicturesqueDatabase::class.java,
            PicturesqueDatabase.DATABASE_NAME
        ).build()

    }

}