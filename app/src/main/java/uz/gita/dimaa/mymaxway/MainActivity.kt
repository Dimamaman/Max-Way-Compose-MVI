package uz.gita.dimaa.mymaxway

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dimaa.mymaxway.app.App
import uz.gita.dimaa.mymaxway.connection.ConnectivityObserver
import uz.gita.dimaa.mymaxway.connection.NetworkConnectivityObserver
import uz.gita.dimaa.mymaxway.navigation.NavigationHandler
import uz.gita.dimaa.mymaxway.presenter.screens.splash.SplashScreen
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigatorHandler: NavigationHandler

    private lateinit var connectivityObserver: ConnectivityObserver

    @SuppressLint("FlowOperatorInvokedInComposition", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityObserver = NetworkConnectivityObserver(App.instance.applicationContext)

        setContent {

            val status by connectivityObserver.observe().collectAsState(
                initial = ConnectivityObserver.Status.Unavailable
            )

            when(status) {
                ConnectivityObserver.Status.Unavailable -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(painter = painterResource(id = R.drawable.no_wifi), contentDescription = "")
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = "No Internet Connection")
                    }
                }

                ConnectivityObserver.Status.Available -> {
                    Navigator(screen = SplashScreen()) { navigator ->
                        navigatorHandler.navigatorBuffer
                            .onEach { it.invoke(navigator) }
                            .launchIn(lifecycleScope)
                        CurrentScreen()
                    }
                }

                ConnectivityObserver.Status.Lost -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(painter = painterResource(id = R.drawable.no_wifi), contentDescription = "")
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = "No Internet Connection")
                    }
                }
                ConnectivityObserver.Status.Losing -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(painter = painterResource(id = R.drawable.no_wifi), contentDescription = "")
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = "No Internet Connection")
                    }
                }
            }
        }
    }
}