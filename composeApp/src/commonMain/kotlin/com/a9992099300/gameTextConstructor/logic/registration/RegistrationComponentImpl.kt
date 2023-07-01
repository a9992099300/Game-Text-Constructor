package com.a9992099300.gameTextConstructor.logic.registration

import com.a9992099300.gameTextConstructor.data.auth.repository.AuthRepository
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class RegistrationComponentImpl (
    componentContext: ComponentContext,
    private val backClicked: () -> Unit,
) : ComponentContext by componentContext, RegistrationComponent {

    private val backCallback = BackCallback {
    /* Handle the back button */
    }

    init {
        backHandler.register(backCallback)
    }

    private fun updateBackCallback() {
        // Set isEnabled to true if you want to override the back button
        backCallback.isEnabled = true // or false
    }

    private val authRepository: AuthRepository = Inject.instance()

    override val stateUi: MutableStateFlow<StateUi<Unit>> =
        MutableStateFlow(StateUi.Initial)

    override val login: MutableStateFlow<String> = MutableStateFlow("")

    override val password: MutableStateFlow<String>  = MutableStateFlow("")

    private val signInRetainedInstance =
        instanceKeeper.getOrCreate { SignInRetainedInstance(Dispatchers.Default) }

    override fun onLoginChanged(login: String) {
        this.login.value = login
    }

    override fun onPasswordChanged(password: String) {
        this.password.value = password
    }

    override fun onRegistrationClick() {
        this.stateUi.value = StateUi.Loading
        signInRetainedInstance.signIn()
    }

    override fun onBack() {
        backClicked()
    }



    inner class SignInRetainedInstance(mainContext: CoroutineContext) : InstanceKeeper.Instance {
        private val scope = CoroutineScope(mainContext + SupervisorJob())

        fun signIn() {
            scope.launch {
                val result = authRepository.registration(
                    login.value,
                    password.value
                )
                when (result) {
                    is Result.Success -> stateUi.value = StateUi.Success(Unit)
                    is Result.Empty ->  stateUi.value = StateUi.Empty
                    is Result.Error ->  stateUi.value = StateUi.Error(result.error?.message ?: "Error")
                }
            }
        }

        override fun onDestroy() {
            scope.cancel() // Cancel the scope when the instance is destroyed
        }
    }
}