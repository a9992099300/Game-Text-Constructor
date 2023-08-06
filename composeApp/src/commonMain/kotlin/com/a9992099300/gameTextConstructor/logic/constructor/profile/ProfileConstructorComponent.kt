package com.a9992099300.gameTextConstructor.logic.constructor.profile

import com.a9992099300.gameTextConstructor.data.profile.models.ProfileDataModel
import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import kotlinx.coroutines.flow.StateFlow

interface ProfileConstructorComponent: BaseComponent<ProfileDataModel> {

    val name: StateFlow<String>

    fun onNameChanged(name: String)
    fun onSaveChanged()

    sealed class Main {
        object Exit : Main()
    }

}