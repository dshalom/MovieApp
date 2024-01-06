package com.ds.movieapp.di

import com.ds.movieapp.data.repo.RepoImpl
import com.ds.movieapp.domain.Repo
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
    abstract fun bindsRepository(
        tasksRepository: RepoImpl
    ): Repo
}
