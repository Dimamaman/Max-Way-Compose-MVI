package uz.gita.dimaa.mymaxway.presenter.page.home

import uz.gita.dimaa.mymaxway.navigation.AppNavigator
import uz.gita.dimaa.mymaxway.presenter.page.orders.OrdersPage
import javax.inject.Inject

class HomeDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : HomeContract.Direction{
    override suspend fun goOrderPage() {
        appNavigator.navigateTo(OrdersPage())
    }
}