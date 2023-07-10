package com.a9992099300.gameTextConstructor.logic.constructor.listBooks

interface ListBookConstructorComponent {

    sealed class ListBook {
        object Main : ListBook()
    }
}