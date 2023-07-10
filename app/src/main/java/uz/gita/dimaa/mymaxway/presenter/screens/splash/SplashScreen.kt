package uz.gita.dimaa.mymaxway.presenter.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.dimaa.mymaxway.R
import uz.gita.dimaa.mymaxway.app.App
import uz.gita.dimaa.mymaxway.connection.ConnectivityObserver
import uz.gita.dimaa.mymaxway.connection.NetworkConnectivityObserver

class SplashScreen : AndroidScreen() {

    private lateinit var connectivityObserver: ConnectivityObserver

    @Composable
    override fun Content() {
        connectivityObserver = NetworkConnectivityObserver(App.instance.applicationContext)

        val status by connectivityObserver.observe()
            .collectAsState(initial = ConnectivityObserver.Status.Unavailable)

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
                val viewModel: SplashContract.ViewModel = getViewModel<SplashScreenViewModel>()
                val uiState = viewModel.uiState.collectAsState()
                SplashScreenContent(uiState, viewModel::onEventDispatcher)
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

@Composable
fun SplashScreenContent(
    uiState: State<SplashContract.UIState>,
    onEventDispatcher: (SplashContract.Intent) -> Unit
) {

    onEventDispatcher.invoke(SplashContract.Intent.Loading)

    Surface(modifier = Modifier.fillMaxSize()) {
        if (uiState.value.isInternetAvailable) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        modifier = Modifier.size(150.dp),
                        painter = painterResource(id = R.drawable.no_wifi),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "No Internet", fontSize = 22.sp)
                }
            }
        } else {
            Image(
                painter = painterResource(id = R.drawable.max_way_1),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
    }
}