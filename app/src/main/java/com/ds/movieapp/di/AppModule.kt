package com.ds.movieapp.di

import com.ds.movieapp.data.homeRepo.HomeRepoImpl
import com.ds.movieapp.data.profileRepo.ProfileRepoImpl
import com.ds.movieapp.domain.repo.HomeRepo
import com.ds.movieapp.domain.repo.ProfileRepo
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
    abstract fun bindsHomeRepository(
        tasksRepository: HomeRepoImpl
    ): HomeRepo

    @Binds
    @Singleton
    abstract fun bindsProfileRepository(
        tasksRepository: ProfileRepoImpl
    ): ProfileRepo
}
