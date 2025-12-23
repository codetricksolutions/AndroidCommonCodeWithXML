package com.cts.common.code.di

import android.content.Context
import com.cts.common.code.session.EncryptedSharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EncryptedPrefsModule {

    @Provides
    @Singleton
    fun provideEncryptedSharedPrefs(
        @ApplicationContext context: Context
    ): EncryptedSharedPrefs {
        return EncryptedSharedPrefs(context, BuildConfig.LOCAL_STORAGE_NAME,BuildConfig.LOCAL_STORAGE_ALIAS)
    }
}