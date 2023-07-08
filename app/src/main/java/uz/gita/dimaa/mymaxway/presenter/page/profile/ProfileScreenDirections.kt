package uz.gita.dimaa.mymaxway.presenter.page.profile

import uz.gita.dimaa.mymaxway.navigation.AppNavigator
import javax.inject.Inject

class ProfileScreenDirections @Inject constructor(private val navigator: AppNavigator) :
    ProfileContact.Directions {
    override suspend fun openEditProfileScreen() {
//        navigator.navigateTo(EditProfileScreen())
    }
}