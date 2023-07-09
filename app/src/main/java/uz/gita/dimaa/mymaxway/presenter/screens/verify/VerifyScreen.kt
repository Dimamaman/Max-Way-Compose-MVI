package uz.gita.dimaa.mymaxway.presenter.screens.verify

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.dimaa.mymaxway.navigation.AppScreen
import uz.gita.dimaa.mymaxway.presenter.screens.login.LoginScreenContent
import uz.gita.dimaa.mymaxway.presenter.screens.login.LoginScreenContract
import uz.gita.dimaa.mymaxway.presenter.screens.login.component.CommonLoginButton
import uz.gita.dimaa.mymaxway.presenter.screens.login.component.CommonText
import uz.gita.dimaa.mymaxway.theme.LightGrayColor
import uz.gita.dimaa.mymaxway.theme.dark_grey
import uz.gita.dimaa.mymaxway.util.OtpView

class VerifyScreen : AppScreen() {
    @Composable
    override fun Content() {
        val viewModel: VerifyScreenContract.ViewModel = getViewModel<VerifyScreenViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        VerifyScreenContent(uiState, viewModel::ovEventDispatcher,context)
    }
}

@Composable
fun VerifyScreenContent(
    uiState: State<VerifyScreenContract.UIState>,
    onEventDispatcher: (VerifyScreenContract.Intent) -> Unit,
    context: Context
) {
    var smsCode by remember { mutableStateOf("") }


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp, top = 20.dp, bottom = 20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CommonText(text = "Enter SMS code,", fontSize = 34.sp, fontWeight = FontWeight.Normal,color = dark_grey) {}
            Spacer(modifier = Modifier.height(5.dp))

            Spacer(modifier = Modifier.height(20.dp))

            OtpView(otpText = smsCode, modifier = Modifier.fillMaxWidth()) {
                smsCode = it
            }

            Spacer(modifier = Modifier.height(142.dp))

            CommonLoginButton(text = "Next", modifier = Modifier.fillMaxWidth()) {
                if (smsCode.isNotEmpty()) {

                    onEventDispatcher(VerifyScreenContract.Intent.Verify(smsCode))

                } else {
                    Toast.makeText(context, "Please fill data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


@Preview
@Composable
fun VerifyScreenContentPreview() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        VerifyScreenContent(
            uiState = remember {
                mutableStateOf(VerifyScreenContract.UIState.Loading)
            },
            onEventDispatcher = {

            },
            context = context
        )
    }
}