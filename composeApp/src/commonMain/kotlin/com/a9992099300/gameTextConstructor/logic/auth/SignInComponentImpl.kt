package com.a9992099300.gameTextConstructor.logic.auth

import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.auth.repository.AuthRepository
import com.a9992099300.gameTextConstructor.di.Inject.instance
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SignInComponentImpl(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, SignInComponent {

    private val authRepository: AuthRepository = instance()

    override val login = MutableStateFlow("")

    override val password = MutableStateFlow("")

    override val inProgress = MutableStateFlow(false)

    private val signInRetainedInstance = instanceKeeper.getOrCreate { SignInRetainedInstance(Dispatchers.Default) }

    override fun onLoginChanged(login: String) {
        this.login.value = login
    }

    override fun onPasswordChanged(password: String) {
        this.password.value = password
    }

    override fun onSignInClick() {
        signInRetainedInstance.signIn()
    }

    inner class SignInRetainedInstance(mainContext: CoroutineContext) : InstanceKeeper.Instance {
        // The scope survives Android configuration changes
        private val scope = CoroutineScope(mainContext + SupervisorJob())

        fun signIn() {
            scope.launch {
                 when(authRepository.signIn(login.value, password.value))  {
                     is Result.Success -> {}
                     is Result.Empty -> {}
                     is Result.Error -> {}
                     }
                 }
                println("body")
        }

        override fun onDestroy() {
            scope.cancel() // Cancel the scope when the instance is destroyed
        }
    }
}


