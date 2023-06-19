package uz.gita.dimaa.mymaxway.navigation

import kotlinx.coroutines.flow.Flow


interface NavigationHandler {
    val navigatorBuffer:Flow<NavigationArg>
}