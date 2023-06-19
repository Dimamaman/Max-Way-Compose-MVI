package uz.gita.dimaa.mymaxway.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.dimaa.mymaxway.domain.repository.AuthRepository
import uz.gita.dimaa.mymaxway.domain.repository.AuthRepositoryImpl


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {


    @Binds
    fun bindAppRepository(impl: AuthRepositoryImpl): AuthRepository

}