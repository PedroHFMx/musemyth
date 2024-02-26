package com.example.purecompose.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.util.Locale

data class THP(
    var hElabTitle: String? = "What to elaborate?",
    var hElabBody: String? = "Select a service below and discuss it with our specialists",
    var hAppCardTitle: String? = "Application",
    var hAppCardBody: String? = "Your App, Software, Website idea will get off the ground!",
    var hVisuIdenCardTitle: String? = "Visual Identity",
    var hVisuIdenCardBody: String? = "Get the respectfull look your brand deserves!",
    var hProjectsTitle: String? = "Your projects:",
    var hProjectsCardTitle: String? = "Accompany project",
    var hProjectsCardBody: String? = "If you have a project code, click here to insert",
    var hAppVersion: String? = "App version:",
    var hWapp1: String? = "Hi there! I would like to elaborate an aplication with UX Studios!",
    var hWapp2: String? = "Hi there! I would like to elaborate a visual identity with UX Studios!",
)

var systemLang: String by mutableStateOf(Locale.getDefault().language)


fun setTHP(): THP {

    return when (systemLang) {

        "pt" -> THP(
            "O que vamos elaborar?",
            "Selecione um serviço a seguir, e elabore com nossos especialistas",
            "Aplicação",
            "Sua ideia de App, Software, Site vai sair do papel!",
            "Identidade Visual",
            "Tenha o visual de respeito que sua marca merece!",
            "Seus projetos:",
            "Acompanhar projeto",
            "Se você tem um código de projeto, clique para inserir",
            "Versão do app:",
            "Olá! Gostaria de elaborar uma aplicação com a UX Studios!",
            "Olá! Gostaria de elaborar uma identidade visual com a UX Studios!",
        )


        "en" -> THP()


        else -> THP()
    }

}
