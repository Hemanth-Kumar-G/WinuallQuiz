package com.hemanth.winuhall.di


import com.hemanth.winuhall.data.RepoService
import com.hemanth.winuhall.data.repository.HomeRepository
import com.hemanth.winuhall.data.repositoryImpl.HomeRepositoryImpl
import dagger.Module
import dagger.Provides


@Module
class RepositoryModule {

    @Provides
    fun provideHomeRepo( repoService: RepoService): HomeRepository = HomeRepositoryImpl(repoService)

}