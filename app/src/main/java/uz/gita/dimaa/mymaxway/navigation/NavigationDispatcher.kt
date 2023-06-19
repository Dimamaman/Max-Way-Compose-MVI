package uz.gita.dimaa.mymaxway.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import uz.gita.dimaa.mymaxway.util.myLog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationDispatcher @Inject constructor() : AppNavigator, NavigationHandler {

    override val navigatorBuffer = MutableSharedFlow<NavigationArg>()

    private suspend fun navigate(args: NavigationArg) {
        navigatorBuffer.emit(args)
    }

    override suspend fun replaceAll(screen: AppScreen) = navigate {
        replaceAll(screen)
    }

    override suspend fun stackLog() = navigate {
        myLog("-----------------------------")
        items.forEach {
            myLog(it.toString())
        }
        myLog("-----------------------------")
    }

    override suspend fun back() = navigate {
        pop()
    }

    override suspend fun backUntilRoot() = navigate {
        popUntilRoot()
    }

    override suspend fun backAll() = navigate {
        popAll()
    }

    override suspend fun navigateTo(screen: AppScreen) = navigate {
        push(screen)
    }

    override suspend fun replace(screen: AppScreen) = navigate {
        replace(screen)
    }

}