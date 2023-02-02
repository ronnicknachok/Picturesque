package app.picturesque.di

import app.picturesque.data.repository.Repository
import app.picturesque.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun imageRepository(imagerRepositoryImpl: RepositoryImpl): Repository

}