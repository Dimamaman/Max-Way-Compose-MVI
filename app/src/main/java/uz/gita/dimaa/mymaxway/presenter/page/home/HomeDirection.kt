package uz.gita.dimaa.mymaxway.presenter.page.home

import uz.gita.dimaa.mymaxway.data.model.Food
import uz.gita.dimaa.mymaxway.navigation.AppNavigator
import javax.inject.Inject

class HomeDirection @Inject constructor(
    private val appNavigator: AppNavigator
): HomeContract.Direction {
    override suspend fun openDialog(food: Food) {
//        appNavigator.navigateTo()
    }
}