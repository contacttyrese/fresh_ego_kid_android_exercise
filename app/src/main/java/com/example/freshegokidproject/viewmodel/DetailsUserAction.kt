package com.example.freshegokidproject.viewmodel

sealed class DetailsUserAction {
    object AwaitingSelection : DetailsUserAction()
    object AddToCart : DetailsUserAction()
}