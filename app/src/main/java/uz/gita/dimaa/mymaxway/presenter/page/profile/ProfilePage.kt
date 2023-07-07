package uz.gita.dimaa.mymaxway.presenter.page.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.dimaa.mymaxway.R

class ProfilePage : Tab, AndroidScreen() {
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

    }
}