package com.cts.common.code.di


import com.cts.common.code.data.repository.auth.login.LoginRepository
import com.cts.common.code.data.repository.auth.login.LoginRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {


    @Provides
    fun provideLoginRepository(logRepo: LoginRepositoryImp): LoginRepository = logRepo
}