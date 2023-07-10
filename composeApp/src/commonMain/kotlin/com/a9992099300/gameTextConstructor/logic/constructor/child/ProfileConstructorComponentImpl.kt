package com.a9992099300.gameTextConstructor.logic.constructor.child

import com.arkivanov.decompose.ComponentContext

class ProfileConstructorComponentImpl(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, ProfileConstructorComponent
