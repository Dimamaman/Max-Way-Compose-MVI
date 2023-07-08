package uz.gita.dimaa.mymaxway.presenter.screens.busket

import uz.gita.dimaa.mymaxway.navigation.AppNavigator
import javax.inject.Inject

class BasketDirection @Inject constructor(
    private val appNavigator: AppNavigator
): BasketContract.Direction {
    override suspend fun back() {
        appNavigator.back()
    }
}