package com.example.codechallenge.framework.requestmanager.di

import com.example.codechallenge.framework.requestmanager.ApiConstants
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider(): String = ApiConstants.BASE_API_URL

}