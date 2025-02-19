package diyanweb.kshdriver.ir.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import diyanweb.kshdriver.ir.DiyanWebApplication
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext // Provide the application context
    }

    @Provides
    fun provideDiyanWebApplication(application: Application): DiyanWebApplication {
        return application as DiyanWebApplication
    }
}
