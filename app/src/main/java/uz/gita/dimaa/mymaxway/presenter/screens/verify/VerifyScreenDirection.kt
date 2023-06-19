package uz.gita.dimaa.mymaxway.presenter.screens.verify

import uz.gita.dimaa.mymaxway.navigation.AppNavigator
import uz.gita.dimaa.mymaxway.presenter.screens.main.MainScreen
import javax.inject.Inject

class VerifyScreenDirection @Inject constructor(
    private val appNavigator: AppNavigator
): VerifyScreenContract.Direction {
    override suspend fun openMainScreen() {
        appNavigator.navigateTo(MainScreen())
    }
}