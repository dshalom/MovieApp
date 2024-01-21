package com.ds.movieapp.di

import com.ds.movieapp.data.repo.authenticationRepo.AuthenticationRepoImpl
import com.ds.movieapp.data.repo.homeRepo.MoviesRepoImpl
import com.ds.movieapp.data.repo.homeRepo.StoreRepo
import com.ds.movieapp.data.repo.homeRepo.StoreRepoImpl
import com.ds.movieapp.data.repo.watchListFavoritesRepo.WatchListFavoritesRepoImpl
import com.ds.movieapp.domain.repo.AuthenticationRepo
import com.ds.movieapp.domain.repo.MoviesRepo
import com.ds.movieapp.domain.repo.WatchListFavoritesRepo
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
    abstract fun bindsAuthenticationRepo(
        authenticationRepo: AuthenticationRepoImpl
    ): AuthenticationRepo

    @Binds
    @Singleton
    abstract fun bindsStoreRepoRepo(
        storeRepoRepo: StoreRepoImpl
    ): StoreRepo

    @Binds
    @Singleton
    abstract fun bindsWatchListFavoritesRepo(
        sessionRepo: WatchListFavoritesRepoImpl
    ): WatchListFavoritesRepo
}
