package com.ds.movieapp.di

import com.ds.movieapp.data.homeRepo.MoviesRepoImpl
import com.ds.movieapp.data.homeRepo.StoreRepo
import com.ds.movieapp.data.homeRepo.StoreRepoImpl
import com.ds.movieapp.data.profileRepo.AuthenticationRepoImpl
import com.ds.movieapp.domain.repo.AuthenticationRepo
import com.ds.movieapp.domain.repo.MoviesRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindsMoviesRepository(
        moviesRepository: MoviesRepoImpl
    ): MoviesRepo

    @Binds
    @Singleton
    abstract fun bindsProfileRepo(
        tasksRepository: AuthenticationRepoImpl
    ): AuthenticationRepo

    @Binds
    @Singleton
    abstract fun bindsSessionRepo(
        tasksRepository: StoreRepoImpl
    ): StoreRepo
}
