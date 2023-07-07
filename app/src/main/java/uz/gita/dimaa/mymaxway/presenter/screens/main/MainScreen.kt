package uz.gita.dimaa.mymaxway.presenter.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import uz.gita.dimaa.mymaxway.navigation.AppScreen
import uz.gita.dimaa.mymaxway.presenter.page.home.HomePage
import uz.gita.dimaa.mymaxway.presenter.page.orders.OrdersPage
import uz.gita.dimaa.mymaxway.presenter.page.profile.ProfilePage

class MainScreen: AppScreen() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        TabNavigator(HomePage()) {
            Scaffold(
                content = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    NavigationBar(
                        modifier = Modifier
                            .height(70.dp)
                            .background(Color.White),
                        contentColor = Color.White,
                        containerColor = Color.White
                    ) {
                        TabNavigationItem(tab = HomePage())
                        TabNavigationItem(tab = OrdersPage())
                        TabNavigationItem(tab = ProfilePage())
                    }
                }
            )
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        alwaysShowLabel = true,
        label = {
            Text(
                text = tab.options.title,
                modifier = Modifier,
                color = if (tabNavigator.current == tab) Color(0xFF50267D) else Color.Black,
                fontSize = 11.sp
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
            indicatorColor = Color.White
        ),
        icon = {
            Icon(
                painter = tab.options.icon!!,

                contentDescription = tab.options.title,
                tint = if (tabNavigator.current == tab) Color(0xFF50267D) else Color.Black
            )
        }

    )
}