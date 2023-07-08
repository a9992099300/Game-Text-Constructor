package com.a9992099300.gameTextConstructor.logic.login

import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.auth.repository.AuthRepository
import com.a9992099300.gameTextConstructor.di.Inject.instance
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginComponentImpl(
    private val componentContext: ComponentContext,
    private val registrationClicked: () -> Unit,
) : ComponentContext by componentContext, LogInComponent {

    private val authRepository: AuthRepository = instance()

    override val stateUi: MutableStateFlow<StateUi<Unit>> =
        MutableStateFlow(StateUi.Initial)

    override val login = MutableStateFlow("")

    override val password = MutableStateFlow("")

    private val signInRetainedInstance =
        instanceKeeper.getOrCreate { SignInRetainedInstance(Dispatchers.Default) }

    override fun onLoginChanged(login: String) {
        this.login.value = login
    }

    override fun onPasswordChanged(password: String) {
        this.password.value = password
    }

    override fun onSignInClick() {
        this.stateUi.value = StateUi.Loading
        signInRetainedInstance.signIn()
    }

    override fun onRegistrationClick() {
        registrationClicked()
    }

    inner class SignInRetainedInstance(mainContext: CoroutineContext) : InstanceKeeper.Instance {
        // The scope survives Android configuration changes
        private val scope = CoroutineScope(mainContext + SupervisorJob())

        fun signIn() {
            scope.launch {
                val result = authRepository.login(
                    login.value,
                    password.value
                )
                when (result) {
                    is Result.Success -> {
                        result.value?.let { authRepository.saveTokens(it) }
                        stateUi.value = StateUi.Success(Unit)
                    }
                    is Result.Empty ->  stateUi.value = StateUi.Empty
                    is Result.Error -> {
                        Napier.d("error ${result.error?.cause?.message}")
                        stateUi.value = StateUi.Error(result.error?.message ?: "Error")
                    }
                }
            }
        }

        override fun onDestroy() {
            scope.cancel() // Cancel the scope when the instance is destroyed
        }
    }
}





