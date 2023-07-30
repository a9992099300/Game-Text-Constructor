package com.a9992099300.gameTextConstructor.ui.screen.models

data class SceneUIModel(
    val sceneId: String,
  //  val sceneNumber: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val deletable: Boolean,
    val selected: Boolean,
)