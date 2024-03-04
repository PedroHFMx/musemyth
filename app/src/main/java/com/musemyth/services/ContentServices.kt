package com.musemyth.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.firestore.FirebaseFirestore
import com.musemyth.model.Character
import com.musemyth.model.Storyline

var storylineTables by mutableStateOf(emptyList<Storyline>())
var characterTables by mutableStateOf(emptyList<Character>())
var isLoadingStories by mutableStateOf( false)
var isLoadingCharacters by mutableStateOf( false)

fun fetchStorylinesTables() {
    val storylinesList = arrayListOf<Storyline>()
    isLoadingStories = true
     FirebaseFirestore.getInstance().collection("storyline")
        .addSnapshotListener { value, error ->
            isLoadingStories = false
            if (error != null) {
                return@addSnapshotListener
            }
            if(value != null){
                storylinesList.clear()

            for (doc in value.documents){
                val storyline = doc.toObject(Storyline::class.java)
                val newStoryline = Storyline(
                    table = storyline?.table
                )
                storylinesList.add(newStoryline)
             }
            }

            storylineTables = storylinesList
        }
}

fun fetchCharactersTables() {
    val charactersList = arrayListOf<Character>()
    isLoadingStories = true
     FirebaseFirestore.getInstance().collection("character")
        .addSnapshotListener { value, error ->
            isLoadingStories = false
            if (error != null) {
                // Handle error
                return@addSnapshotListener
            }
            if(value != null){
                charactersList.clear()

            for (doc in value.documents){
                val storyline = doc.toObject(Character::class.java)
                val newStoryline = Character(
                    table = storyline?.table
                )
                charactersList.add(newStoryline)
             }
            }
            characterTables = charactersList
        }
}
fun fetchStorylinesActions() {
    val storylinesList = arrayListOf<Storyline>()
    isLoadingStories = true
    FirebaseFirestore.getInstance().collection("storyline")
        .get()
        .addOnCompleteListener { task ->
            isLoadingStories = false
            if (task.isSuccessful) {
                for (doc in task.result) {
                    val storyline = doc.toObject(Storyline::class.java)
                    val newStoryline = Storyline(
                        table = storyline.table
                    )
                    storylinesList.add(newStoryline)
                }
                storylineTables = storylinesList
            }
        }
}