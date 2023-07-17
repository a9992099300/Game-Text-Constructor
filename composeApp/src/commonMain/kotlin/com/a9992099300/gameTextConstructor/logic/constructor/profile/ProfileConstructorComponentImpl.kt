package com.a9992099300.gameTextConstructor.logic.constructor.profile

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
import kotlin.coroutines.CoroutineContext

class ProfileConstructorComponentImpl(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, ProfileConstructorComponent {

    private val userRepository: UserRepository = Inject.instance()

    override val stateUi: MutableStateFlow<StateUi<ProfileDataModel>> =
        MutableStateFlow(StateUi.Initial)

    override val name = MutableStateFlow("")

    override fun onNameChanged(name: String) {
         this.name.value = name
    }

    override fun onSaveChanged() {
        this.stateUi.value = StateUi.Loading
        profileRetainedInstance.saveUserData()
    }

    private val profileRetainedInstance =
        instanceKeeper.getOrCreate { ProfileRetainedInstance(Dispatchers.Default) }


    inner class ProfileRetainedInstance(mainContext: CoroutineContext) : InstanceKeeper.Instance {
        // The scope survives Android configuration changes

        private val scope = CoroutineScope(mainContext + SupervisorJob())
        init {
            getUserData()
        }

        fun saveUserData() {
            scope.launch {
                val result = userRepository.updateUserInfo(
                    name.value
                )
                workWithResult(result)
            }
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
                    stateUi.value = StateUi.Success(result.value)
                    name.emit(result.value.name)
                }
                is Result.Error -> {
                    stateUi.value = StateUi.Error(result.error?.message ?: "Error")
                }
            }
        }

        override fun onDestroy() {
            scope.cancel() // Cancel the scope when the instance is destroyed
        }
    }

}
