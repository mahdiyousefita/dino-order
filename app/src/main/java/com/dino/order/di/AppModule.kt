package com.dino.order.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.dino.order.DinoOrderApplication

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext // Provide the application context
    }

    @Provides
    fun provideDiyanWebApplication(application: Application): DinoOrderApplication {
        return application as DinoOrderApplication
    }
}
