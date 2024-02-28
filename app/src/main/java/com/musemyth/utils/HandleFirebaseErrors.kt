package com.musemyth.utils

import androidx.compose.runtime.Composable
import com.musemyth.components.ShowModal
import com.musemyth.services.showModal

@Composable
fun HandleFirebaseError(e: String) {
     when (e) {
        "There is no user record corresponding to this identifier. The user may have been deleted." ->
            return ShowModal(
                title = "Ops!",
                content = "Email incorreto ou não cadastrado.",
                confirmBtnTxt = "Tentar novamente",
                onDismiss = { showModal = false })

        "The password is invalid or the user does not have a password." ->
            return ShowModal(
                title = "Ops!",
                content = "Senha incorreta.",
                confirmBtnTxt = "Tentar novamente",
                onDismiss = { showModal = false })

         "The email address is already in use by another account." ->
            return ShowModal(
                title = "Ops!",
                content = "Email já cadastrado.",
                confirmBtnTxt = "Tentar novamente",
                onDismiss = { showModal = false })

         "A network error (such as timeout, interrupted connection or unreachable host) has occurred." ->
            return ShowModal(
                title = "Ops!",
                content = "Verifique sua conexão com a internet e tente novamente.",
                confirmBtnTxt = "Tentar novamente",
                onDismiss = { showModal = false })

        else ->
            return ShowModal(
                title = "Ops!",
                content = "Um erro inesperado ocorreu.",
                confirmBtnTxt = "Tentar novamente",
                onDismiss = { showModal = false })

    }
}