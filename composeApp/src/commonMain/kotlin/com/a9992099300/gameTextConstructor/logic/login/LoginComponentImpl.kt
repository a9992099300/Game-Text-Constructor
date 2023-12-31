package com.a9992099300.gameTextConstructor.logic.login

import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.auth.repository.AuthRepository
import com.a9992099300.gameTextConstructor.di.Inject.instance
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class LoginComponentImpl(
    private val componentContext: ComponentContext,
    private val registrationClicked: () -> Unit,
    private val openMain: () -> Unit,
    private val openRootConstructor: () -> Unit,
    private val authRepository: AuthRepository = instance()
) : ComponentContext by componentContext, LogInComponent {

    override val onBack: () -> Unit = {

    }

    override val stateUi: MutableStateFlow<StateUi<Unit>> =
        MutableStateFlow(StateUi.Initial)

    override val login = MutableStateFlow("")

    override val password = MutableStateFlow(Pair("", true))

    private val signInViewModel =
        instanceKeeper.getOrCreate { SignInViewModel(Dispatchers.Default) }

    override fun onLoginChanged(login: String) {
        this.login.value = login
    }

    override fun onPasswordChanged(password: String) {
        this.password.value = this.password.value.copy(first = password)
    }

    override fun onVisibleChanged(visible: Boolean) {
        this.password.value = this.password.value.copy(second = visible)
    }

    override fun onSignInClick() {
        this.stateUi.value = StateUi.Loading
        signInViewModel.signIn()
    }

    override fun onRegistrationClick() {
        registrationClicked()
    }

    inner class SignInViewModel(mainContext: CoroutineContext) : InstanceKeeper.Instance {
        // The scope survives Android configuration changes
        private val scope = CoroutineScope(mainContext + SupervisorJob())

        fun signIn() {
            scope.launch {
                val result = authRepository.login(
                    login.value,
                    password.value.first
                )
                when (result) {
                    is Result.Success -> {
                        result.value?.let { authRepository.saveTokens(it) }
                        stateUi.value = StateUi.Success(Unit)
                        withContext(Dispatchers.Main) {
                            openRootConstructor()
                        }
                    }
                    is Result.Error -> {
                        stateUi.value = StateUi.Error(result.error?.message ?: "Error")
                    }
                }
            }
        }

        override fun onDestroy() {
            scope.cancel()
        }
    }
}





