package com.a9992099300.gameTextConstructor.logic.splash

import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.profile.models.ProfileDataModel
import com.a9992099300.gameTextConstructor.data.profile.repository.UserRepository
import com.a9992099300.gameTextConstructor.di.Inject
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

class SplashComponentImpl(
    componentContext: ComponentContext,
    private val openLogin: () -> Unit,
    private val openMain: () -> Unit,
) : ComponentContext by componentContext, SplashComponent {

    private val userRepository: UserRepository = Inject.instance()

    override val name = MutableStateFlow("")

    override val stateUi: MutableStateFlow<StateUi<Unit>> =
        MutableStateFlow(StateUi.Initial)

    private val signInRetainedInstance =
        instanceKeeper.getOrCreate { SignInRetainedInstance(Dispatchers.Default) }

    inner class SignInRetainedInstance(mainContext: CoroutineContext) : InstanceKeeper.Instance {
        // The scope survives Android configuration changes

        private val scope = CoroutineScope(mainContext + SupervisorJob())
        init {
            getUserData()
        }

        private fun getUserData() {
            scope.launch {
                val result = userRepository.getUserInfo()
                workWithResult(result)
            }
        }

        private suspend fun workWithResult(result: Result<ProfileDataModel>) {
            when (result) {
                is Result.Success -> {
                    stateUi.value = StateUi.Success(Unit)
                    name.emit(result.value.name)
               //     delay(1500) задержку добавить после добавления анимации
                    withContext(Dispatchers.Main) {
                        openMain()
                    }
                }
                is Result.Error -> {
                    withContext(Dispatchers.Main) {
                        openLogin()
                    }
                }
            }
        }

        override fun onDestroy() {
            scope.cancel()
        }
    }

}
