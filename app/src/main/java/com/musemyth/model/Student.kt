package com.musemyth.model

data class Student(
    val name: String? = null,
    val accountType: String? = null,
    val id: String? = null,
    val email: String? = null,
    val generatedChars: List<UserChar>? = null,
    val generatedStory: List<UserStoryline>? = null,
)
