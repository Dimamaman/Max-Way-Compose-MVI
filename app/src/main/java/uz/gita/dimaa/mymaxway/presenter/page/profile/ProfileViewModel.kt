package uz.gita.dimaa.mymaxway.presenter.page.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.dimaa.mymaxway.data.local.sharedPref.SharedPref
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sharedPref: SharedPref,
//    private val directions: ProfileContact.Directions
) :
    ProfileContact.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(ProfileContact.UiState())

    override fun onEventDispatcher(intent: ProfileContact.Intent) {
        when (intent) {
            ProfileContact.Intent.Load -> {
                uiState.update {
                    it.copy(name = sharedPref.name)
                }
                uiState.update {
                    it.copy(phoneNumber = sharedPref.phone)
                }
            }

            ProfileContact.Intent.OpenEditProfileScreen -> {
                viewModelScope.launch {
//                    directions.openEditProfileScreen()
                }
            }
        }
    }
}