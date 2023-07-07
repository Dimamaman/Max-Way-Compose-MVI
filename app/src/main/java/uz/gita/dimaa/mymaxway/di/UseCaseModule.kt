package uz.gita.dimaa.mymaxway.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.dimaa.mymaxway.domain.usecase.HomeUseCase
import uz.gita.dimaa.mymaxway.domain.usecase.impl.HomeUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindHomeUse(impl: HomeUseCaseImpl): HomeUseCase
}