package uz.gita.dimaa.mymaxway.domain.repository

import android.app.Activity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun createUserWithPhone(phone:String, activity: Activity) : Flow<Result<String>>

    fun signWithCredential(otp:String): Flow<Result<String>>
}