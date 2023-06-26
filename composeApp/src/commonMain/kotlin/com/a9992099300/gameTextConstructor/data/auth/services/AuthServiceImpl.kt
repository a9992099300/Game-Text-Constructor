package com.a9992099300.gameTextConstructor.data.auth.services

import com.a9992099300.gameTextConstructor.data.auth.models.AuthRequestBody
import com.a9992099300.gameTextConstructor.data.auth.models.AuthResponseBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path

//import dev.gitlive.firebase.Firebase
//import dev.gitlive.firebase.auth.auth

class AuthServiceImpl(
    private val httpClient: HttpClient
) : AuthService {
//    private val auth = Firebase.auth

    override suspend fun signIn(email: String, password: String): AuthResponseBody =
        httpClient.post {
//                 header("X-RapidAPI-Key", "1c2d65f0bamshaca998eaf20e438p11154ajsnd28dd5bd0887")
//                 header("X-RapidAPI-Host", "marvel-vs-capcom-2.p.rapidapi.com")
            url {
                parameters.append(KEY, KEY_VALUE)
                path("accounts:signUp")
                setBody(
                    AuthRequestBody(
                    email,
                    password,
                    true
                )
                )
            }
        }.body()


    //      emit(Result.Error(Exception()))
//        val user = auth.signInWithEmailAndPassword(email, password).user
//        if (user != null) {
//            emit(Result.Success(user.displayName))
//        } else {
//            emit(Result.Error(Exception()))
//        }
    //   }

    companion object {
        const val KEY = "key"
        const val KEY_VALUE = "AIzaSyBSg1OrmYOuDpO6FqvLc_gPbQD9PwkGinw"
    }
}