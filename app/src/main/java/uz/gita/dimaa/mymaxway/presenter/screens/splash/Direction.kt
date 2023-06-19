package uz.gita.dimaa.mymaxway.presenter.screens.splash

interface Direction {
    suspend fun openMainScreen()
    suspend fun openLoginScreen()
}