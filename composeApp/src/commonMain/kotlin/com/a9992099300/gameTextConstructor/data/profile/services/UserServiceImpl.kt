package com.a9992099300.gameTextConstructor.data.profile.services


import com.a9992099300.gameTextConstructor.data.common.ktor.HttpClientWrapper
import com.a9992099300.gameTextConstructor.data.profile.models.ProfileDataModel
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.path


class UserServiceImpl(
    private val httpClient: HttpClientWrapper,
) : UserService {

    override suspend fun updateUserInfo(model: ProfileDataModel) =
        httpClient.addToken.patch {
            url {
                path("users/${model.userId}/profile.json")
                setBody(
                    model
                )
            }
        }

    override suspend fun getUserInfo(userId: String) =
        httpClient.addToken.get {
            url {
                path("users/${userId}/profile.json")
            }
        }

}