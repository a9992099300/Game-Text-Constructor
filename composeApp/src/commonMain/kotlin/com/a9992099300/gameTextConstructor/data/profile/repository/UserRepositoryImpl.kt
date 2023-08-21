package com.a9992099300.gameTextConstructor.data.profile.repository


import com.a9992099300.gameTextConstructor.data.auth.services.log
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import com.a9992099300.gameTextConstructor.data.common.request
import com.a9992099300.gameTextConstructor.data.profile.models.ProfileDataModel
import com.a9992099300.gameTextConstructor.data.profile.services.UserService
import io.github.aakira.napier.Napier
import io.github.xxfast.kstore.KStore

class UserRepositoryImpl(
    private val userService: UserService,
    private val store: KStore<SavedAuth>,
) : UserRepository {

    override suspend fun updateUserInfo(name: String): Result<ProfileDataModel> =
        request {
            val authData = store.get()
            Napier.d(message = "authData $authData", tag = log)
            userService.updateUserInfo(
                ProfileDataModel(
                    userId = authData?.firstOrNull()?.localId ?: "",
                    name = name,
                    email = authData?.firstOrNull()?.email ?: ""
                )
            )
        }

    override suspend fun getUserInfo(): Result<ProfileDataModel> = request {
        val authData = store.get()
        userService.getUserInfo(
            userId = authData?.firstOrNull()?.localId ?: ""
        )
    }
}