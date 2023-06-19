package uz.gita.dimaa.mymaxway.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.dimaa.mymaxway.presenter.screens.login.LoginScreenContract
import uz.gita.dimaa.mymaxway.presenter.screens.login.LoginScreenDirection
import uz.gita.dimaa.mymaxway.presenter.screens.verify.VerifyScreenContract
import uz.gita.dimaa.mymaxway.presenter.screens.verify.VerifyScreenDirection

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @Binds
    fun bindLoginDirection(impl: LoginScreenDirection): LoginScreenContract.Direction

    @Binds
    fun bindVerifyDirection(impl: VerifyScreenDirection): VerifyScreenContract.Direction

}