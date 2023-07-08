package uz.gita.dimaa.mymaxway.presenter.page.profile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.dimaa.mymaxway.R
import uz.gita.dimaa.mymaxway.navigation.AppScreen


class ProfileScreen : Tab, AppScreen() {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Profile"
            val icon = painterResource(id = R.drawable.ic_profile)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: ProfileContact.ViewModel = getViewModel<ProfileViewModel>()
        val uiState = viewModel.uiState.collectAsState().value

        HomeScreenContent(uiState, viewModel::onEventDispatcher)
    }

    @Composable
    private fun HomeScreenContent(
        uiState: ProfileContact.UiState,
        onEventDispatcher: (ProfileContact.Intent) -> Unit
    ) {

        val context = LocalContext.current

        onEventDispatcher(ProfileContact.Intent.Load)
        Surface(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF4F4F4))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.White),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Profile",
                        modifier = Modifier
                            .background(Color.White)
                            .align(Alignment.CenterVertically),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .height(95.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {

                        if (uiState.name.isNotEmpty()) {
                            Column {
                                Text(
                                    text = "Ism",
                                    fontSize = 12.sp,
                                    color = Color.LightGray,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                Text(
                                    text = uiState.name,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                            }
                        }

                        Column {
                            Text(text = "Telefon raqam", fontSize = 12.sp, color = Color.LightGray)
                            Text(text = uiState.phoneNumber)
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clip(RoundedCornerShape(50))
                            .clickable {
                                onEventDispatcher(ProfileContact.Intent.OpenEditProfileScreen)
                            })
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(Color.White)
                            .clickable { showComingSoonToast(context) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.padding(start = 12.dp, end = 8.dp)
                        )


                        Text(text = "Mening manzillarim")
                        Spacer(
                            modifier = Modifier
                                .width(0.dp)
                                .weight(1f)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(32.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .background(Color(0xFFB4B2B2))
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(Color.White)
                            .clickable { showComingSoonToast(context) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_loaction_2),
                            contentDescription = null,
                            modifier = Modifier.padding(start = 12.dp, end = 8.dp)
                        )


                        Text(text = "Filiallar")
                        Spacer(
                            modifier = Modifier
                                .width(0.dp)
                                .weight(1f)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(32.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .background(Color(0xFFB4B2B2))
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(Color.White)
                            .clickable { showComingSoonToast(context) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            modifier = Modifier.padding(start = 12.dp, end = 8.dp)
                        )


                        Text(text = "Sozlamalar")
                        Spacer(
                            modifier = Modifier
                                .width(0.dp)
                                .weight(1f)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(32.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .background(Color(0xFFB4B2B2))
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(Color.White)
                            .clickable { showComingSoonToast(context) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.padding(start = 12.dp, end = 8.dp)
                        )


                        Text(text = "Xizmat haqida")
                        Spacer(
                            modifier = Modifier
                                .width(0.dp)
                                .weight(1f)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(32.dp)
                        )
                    }
                }

                Spacer(
                    modifier = Modifier
                        .height(0.dp)
                        .weight(1f)
                )

                Text(
                    text = "Version:1.0.0",
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 4.dp),
                    fontSize = 12.sp
                )
            }
        }
    }

    private fun showComingSoonToast(context: Context) {
        Toast.makeText(context, "Come soon", Toast.LENGTH_SHORT).show()
    }
}