package uz.gita.dimaa.mymaxway.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.dimaa.mymaxway.domain.repository.auth.AuthRepository
import uz.gita.dimaa.mymaxway.domain.repository.auth.AuthRepositoryImpl
import uz.gita.dimaa.mymaxway.domain.repository.firebase.FirebaseRepository
import uz.gita.dimaa.mymaxway.domain.repository.firebase.FirebaseRepositoryImpl
import uz.gita.dimaa.mymaxway.domain.repository.roomrepository.RoomRepository
import uz.gita.dimaa.mymaxway.domain.repository.roomrepository.RoomRepositoryImpl


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {


    @Binds
    fun bindAppRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindFirebaseRepository(impl: FirebaseRepositoryImpl): FirebaseRepository

    @Binds
    fun bindRoomRepository(impl: RoomRepositoryImpl): RoomRepository
}