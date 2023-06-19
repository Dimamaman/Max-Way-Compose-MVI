package uz.gita.dimaa.mymaxway.presenter.screens.login

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.dimaa.mymaxway.navigation.AppScreen
import uz.gita.dimaa.mymaxway.presenter.screens.login.component.CommonLoginButton
import uz.gita.dimaa.mymaxway.presenter.screens.login.component.CommonText
import uz.gita.dimaa.mymaxway.presenter.screens.login.component.CommonTextField
import uz.gita.dimaa.mymaxway.theme.LightGrayColor

class LoginScreen : AppScreen() {
    @Composable
    override fun Content() {
        val viewModel: LoginScreenContract.ViewModel = getViewModel<LoginScreenViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        LoginScreenContent(uiState, viewModel::onEventDispatcher, context)
    }
}

@Composable
fun LoginScreenContent(
    uiState: State<LoginScreenContract.UIState>,
    onEventDispatcher: (LoginScreenContract.Intent) -> Unit,
    context: Context
) {

    when (uiState.value) {
        is LoginScreenContract.UIState.Loading -> {

        }
    }
    var mobile by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp, top = 20.dp, bottom = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                CommonText(text = "Welcome,", fontSize = 34.sp, fontWeight = FontWeight.Bold) {}
                Spacer(modifier = Modifier.height(5.dp))
                CommonText(
                    text = "Sign in to continue!",
                    fontSize = 28.sp,
                    color = LightGrayColor
                ) {}
            }
            Spacer(modifier = Modifier.height(60.dp))
            CommonTextField(
                text = name,
                placeholder = "Name",
                onValueChange = { name = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CommonTextField(
                text = mobile,
                placeholder = "+998 YY YYY YY YY",
                onValueChange = { mobile = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.height(150.dp))

            CommonLoginButton(text = "Login", modifier = Modifier.fillMaxWidth()) {
                if (mobile.isNotEmpty() && name.isNotEmpty()) {
                    onEventDispatcher.invoke(
                        LoginScreenContract.Intent.Login(
                            mobile,
                            name,
                            context as Activity
                        )
                    )
                } else {
                    Toast.makeText(context, "Please fill data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LoginScreenContent(
            uiState = remember {
                mutableStateOf(LoginScreenContract.UIState.Loading)
            },
            onEventDispatcher = {

            },
            context = context
        )
    }
}











