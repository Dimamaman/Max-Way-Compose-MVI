package uz.gita.dimaa.mymaxway

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dimaa.mymaxway.navigation.NavigationHandler
import uz.gita.dimaa.mymaxway.presenter.screens.splash.SplashScreen
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigatorHandler: NavigationHandler
    @SuppressLint("FlowOperatorInvokedInComposition", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigator(screen = SplashScreen()) { navigator ->
                navigatorHandler.navigatorBuffer
                    .onEach { it.invoke(navigator) }
                    .launchIn(lifecycleScope)
                CurrentScreen()
            }
        }
    }
}